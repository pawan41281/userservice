package com.example.userservice;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class OpenAPIConfiguration {

	@Bean
	OpenAPI defineOpenApi() {
		Server localhost = new Server();
		localhost.setUrl("http://localhost:9999");
		localhost.setDescription("Development");

		Contact myContact = new Contact();
		myContact.setName("Pawan Kumar");
		myContact.setEmail("pawan.kumar@gmail.com");

		Info information = new Info()
								.title("Authuntication API")
								.version("1.0")
								.description("This API exposes endpoints for authuntication service api.")
								.contact(myContact)
								.license(new License().name("Apache 2.0").url("http://springdoc.org"));
		
				
		//return new OpenAPI().info(information).servers(List.of(localhost,vm_4_213_142_244));
		return new OpenAPI().info(information).servers(List.of(localhost));
	}

}
