package com.juju.cozyformombackend3.global.auth.service;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.global.auth.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Override
	public UserPrincipal loadUserByUsername(String email) {
		User user = userRepository.findByEmail(email).orElseThrow();
		return UserPrincipal.of(user);
	}

}
