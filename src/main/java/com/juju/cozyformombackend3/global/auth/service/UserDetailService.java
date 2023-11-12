package com.juju.cozyformombackend3.global.auth.service;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.global.auth.model.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;

public class UserDetailService implements UserDetailsService {

	private final UserRepository userRepository;

	public UserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserPrincipal loadUserByUsername(String email) {
		User user = userRepository.findByEmail(email).orElseThrow();
		return UserPrincipal.of(user);
	}
	
}
