package br.com.antunes.gustavo.pcpartsproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.antunes.gustavo.pcpartsproject.model.LoginRequest;
import br.com.antunes.gustavo.pcpartsproject.model.LoginResponse;
import br.com.antunes.gustavo.pcpartsproject.security.JwtIssuer;

@RestController
public class AuthController {
	
	@Autowired
	private JwtIssuer jwtIssuer;

	@PostMapping("/auth/login")
	public LoginResponse login(@RequestBody @Validated LoginRequest request) {
		var token = jwtIssuer.issue(1L, request.getEmail(), List.of("User"));
		LoginResponse loginResponse = new LoginResponse(token);
		return loginResponse;
	}
}
