package com.example.userservice.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userservice.entity.Role;
import com.example.userservice.entity.User;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.JwtAuthResponse;
import com.example.userservice.service.AuthService;
import com.example.userservice.vo.LoginVo;
import com.example.userservice.vo.MessageResponseVo;
import com.example.userservice.vo.SignupRequestVo;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

//@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AuthService authService;

	@Autowired
	private PasswordEncoder encoder;

	// Build Login REST API
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginVo loginVo) {
		String token = authService.login(loginVo);

		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setAccessToken(token);

		return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
	}

	// Create New User
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestVo signUpRequestVo) {
		if (userRepository.existsByUsername(signUpRequestVo.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponseVo("Error: Username is already exists!"));
		}

		if (userRepository.existsByEmail(signUpRequestVo.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponseVo("Error: Email is already exists!"));
		}

		// Create new user's account
		User user = new User(signUpRequestVo.getName(), signUpRequestVo.getUsername(), signUpRequestVo.getEmail(),
				encoder.encode(signUpRequestVo.getPassword()));

		Set<String> strRoles = signUpRequestVo.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName("ROLE_USER");
			if (userRole == null)
				new RuntimeException("Error: role is not found in database.");
			roles.add(userRole);
					
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "ADMIN":
					Role adminRole = roleRepository.findByName("ROLE_ADMIN");
					if (adminRole == null)
						new RuntimeException("Error: " + role + " role is not found in database.");
					roles.add(adminRole);
					break;

				default:
					Role userRole = roleRepository.findByName(role);
					roles.add(userRole);
				}
			});

		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponseVo("User registered successfully!"));
	}

}