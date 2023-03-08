package br.com.antunes.gustavo.pcpartsproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CustomSecurityConfiguration {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
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
					.requestMatchers("/auth/login").permitAll()
					.anyRequest().authenticated()
			);
		//http.securityMatcher(PathRequest.toH2Console());
		//http.authorizeHttpRequests().requestMatchers("/swagger-ui/**").permitAll();
		//http.authorizeHttpRequests().requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll();
		//http.authorizeHttpRequests().requestMatchers(AntPathRequestMatcher.antMatcher("/tools/**")).permitAll();
		//http.csrf((csrf) -> csrf.disable());
		//http.headers((headers) -> headers.frameOptions().sameOrigin());
		return http.build();
	}

}
