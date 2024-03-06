package com.juju.cozyformombackend3.domain.babylog.baby.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.babylog.baby.dto.request.CreateBabyProfileRequest;
import com.juju.cozyformombackend3.domain.babylog.baby.dto.response.CreateBabyProfileResponse;
import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.babylog.baby.repository.BabyProfileRepository;
import com.juju.cozyformombackend3.domain.user.error.UserErrorCode;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BabyService {
	private final UserRepository userRepository;
	private final BabyProfileRepository babyProfileRepository;

	@Transactional
	public CreateBabyProfileResponse saveBabyProfile(Long userId, CreateBabyProfileRequest request) {
		User user = findUserById(userId);
		final BabyProfile saveBabyProfile = request.toBabyProfile(user);
		saveBabyProfile.addBaby(request.toBabyList(saveBabyProfile));
		final BabyProfile savedBabyProfile = babyProfileRepository.save(saveBabyProfile);

		return CreateBabyProfileResponse.of(savedBabyProfile.getId(),
			savedBabyProfile.getBabyList().stream().map(baby -> baby.getId()).toList());
	}

	private User findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));
	}
}
