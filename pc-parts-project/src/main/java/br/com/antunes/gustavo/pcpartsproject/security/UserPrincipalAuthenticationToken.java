package br.com.antunes.gustavo.pcpartsproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UserPrincipal principal;

	public UserPrincipalAuthenticationToken(UserPrincipal principal) {
		super(principal.getAuthorities());
		this.principal = principal;
		setAuthenticated(true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserPrincipal getPrincipal() {
		// TODO Auto-generated method stub
		return principal;
	}

}
