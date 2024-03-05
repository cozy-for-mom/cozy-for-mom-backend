package com.juju.cozyformombackend3.domain.user.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.babylog.baby.error.BabyErrorCode;
import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.user.dto.object.UserSummary;
import com.juju.cozyformombackend3.domain.user.dto.request.UpdateMyInfoRequest;
import com.juju.cozyformombackend3.domain.user.dto.request.UpdateRecentBabyProfileRequest;
import com.juju.cozyformombackend3.domain.user.dto.response.FindMyInfoResponse;
import com.juju.cozyformombackend3.domain.user.dto.response.UpdateMyInfoResponse;
import com.juju.cozyformombackend3.domain.user.dto.response.UpdateRecentBabyProfileResponse;
import com.juju.cozyformombackend3.domain.user.error.UserErrorCode;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

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
			userRepository.findById(userId).orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER)));

		return FindMyInfoResponse.of(findUserSummary);
	}

	@Transactional
	public UpdateMyInfoResponse updateMe(Long userId, UpdateMyInfoRequest request) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));
		user.update(request);

		return UpdateMyInfoResponse.of(userId);
	}

	@Transactional
	public UpdateRecentBabyProfileResponse updateRecentBabyProfile(Long userId,
		UpdateRecentBabyProfileRequest request) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));
		// user.getBabyProfileList().forEach(profile -> {
		// 	if (Objects.equals(profile.getBabyProfileId(), request.getBabyProfileId())) {
		// 		user.updateRecentBabyProfileId(request.getBabyProfileId());
		// 		return;
		// 	}
		// });

		Optional<BabyProfile> matchingProfile = user.getBabyProfileList().stream()
			.filter(profile -> Objects.equals(profile.getId(), request.getBabyProfileId()))
			.findFirst();

		matchingProfile.ifPresentOrElse(
			profile -> user.updateRecentBabyProfileId(request.getBabyProfileId()),
			() -> {
				throw new BusinessException(BabyErrorCode.NOT_FOUND_BABY_PROFILE);
			}
		);

		return UpdateRecentBabyProfileResponse.of(request.getBabyProfileId());
	}

}
