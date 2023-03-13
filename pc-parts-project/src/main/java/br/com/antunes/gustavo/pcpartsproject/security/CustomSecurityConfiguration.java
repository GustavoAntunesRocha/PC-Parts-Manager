package br.com.antunes.gustavo.pcpartsproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CustomSecurityConfiguration {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private final CustomUserDetailService customUserDetailService;
	
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Bean
	SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
		
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		http
			.cors().disable()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.formLogin().disable()
			.securityMatcher("/**")
			.authorizeHttpRequests(registry -> registry
					.requestMatchers("/").permitAll()
					.requestMatchers("/users/**").permitAll()
					.requestMatchers("/auth/login").permitAll()
					.requestMatchers("/swagger-ui/**").permitAll()
					.requestMatchers("/v3/**").permitAll()
					.anyRequest().authenticated()
			);
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(customUserDetailService)
				.passwordEncoder(passwordEncoder)
				.and().build();
	}
	
}
