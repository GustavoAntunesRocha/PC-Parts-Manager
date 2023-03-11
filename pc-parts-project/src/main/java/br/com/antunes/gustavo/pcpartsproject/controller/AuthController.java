package br.com.antunes.gustavo.pcpartsproject.controller;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.antunes.gustavo.pcpartsproject.model.LoginRequest;
import br.com.antunes.gustavo.pcpartsproject.model.LoginResponse;
import br.com.antunes.gustavo.pcpartsproject.security.JwtIssuer;
import br.com.antunes.gustavo.pcpartsproject.security.UserPrincipal;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	
	@Autowired
	private JwtIssuer jwtIssuer;
	
	private final AuthenticationManager authenticationManager;

	@PostMapping("/auth/login")
	public LoginResponse login(@RequestBody @Validated LoginRequest request) {
		var authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		var principal = (UserPrincipal) authentication.getPrincipal();
		var roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		var token = jwtIssuer.issue(principal.getUserId(), principal.getEmail(), roles);
		return LoginResponse.builder()
				.accesToken(token).build();
	}
}
