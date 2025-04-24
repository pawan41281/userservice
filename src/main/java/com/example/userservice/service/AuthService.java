package com.example.userservice.service;

import com.example.userservice.vo.LoginVo;

public interface AuthService {
	String login(LoginVo loginVo);
}