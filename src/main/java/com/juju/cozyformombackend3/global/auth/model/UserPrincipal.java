package com.juju.cozyformombackend3.global.auth.model;

import com.juju.cozyformombackend3.domain.user.model.User;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

	private final User user;

	private final List<GrantedAuthority> grantedAuthorityList;

	public UserPrincipal(User user) {
		this.user = user;
		if (user.getUserId() == null) {
			this.grantedAuthorityList = List.of(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
		} else {
			this.grantedAuthorityList = List.of(new SimpleGrantedAuthority("ROLE_USER"));
		}
	}

	public static UserPrincipal of(User user) {
		return new UserPrincipal(user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorityList;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return user.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
