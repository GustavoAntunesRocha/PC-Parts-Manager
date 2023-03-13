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

import br.com.antunes.gustavo.pcpartsproject.exception.ApiErrorResponse;
import br.com.antunes.gustavo.pcpartsproject.model.LoginRequest;
import br.com.antunes.gustavo.pcpartsproject.model.LoginResponse;
import br.com.antunes.gustavo.pcpartsproject.security.JwtIssuer;
import br.com.antunes.gustavo.pcpartsproject.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Endpoints for user authentication and authorization")
public class AuthController {
	
	@Autowired
	private JwtIssuer jwtIssuer;
	
	private final AuthenticationManager authenticationManager;

	@Operation(description = "Endpoint for user authentication and to obtain an access token", responses = {
			@ApiResponse(responseCode = "200", description = "Successfully authenticated and received an access token", 
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))),
			@ApiResponse(responseCode = "400", description = "Invalid login request or missing request parameters",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized access or invalid credentials",
				content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
	})
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

