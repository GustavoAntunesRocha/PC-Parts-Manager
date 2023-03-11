package br.com.antunes.gustavo.pcpartsproject.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.antunes.gustavo.pcpartsproject.service.UsersService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService{

	private final UsersService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = userService.findUserByEmail(username);
		return UserPrincipal.builder()
				.userId(user.getId())
				.email(user.getEmail())
				.authorities(List.of(new SimpleGrantedAuthority(user.getRole())))
				.password(user.getPassword())
				.build();
	}

}
