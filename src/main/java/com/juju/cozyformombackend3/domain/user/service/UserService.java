package com.juju.cozyformombackend3.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.user.dto.object.UserSummary;
import com.juju.cozyformombackend3.domain.user.dto.response.FindMyInfoResponse;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public FindMyInfoResponse findMe(Long userId) {
		// UserSummary findUserSummary = userRepository.findUserSummaryById(userId);
		UserSummary findUserSummary = new UserSummary(
			userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저")));

		return FindMyInfoResponse.of(findUserSummary);
	}
}
