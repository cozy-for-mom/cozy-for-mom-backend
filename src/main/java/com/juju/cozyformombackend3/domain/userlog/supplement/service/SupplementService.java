package com.juju.cozyformombackend3.domain.userlog.supplement.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.request.RegisterSupplementRequest;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.request.UpdateSupplementRequest;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.response.RegisterSupplementResponse;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.response.UpdateSupplementResponse;
import com.juju.cozyformombackend3.domain.userlog.supplement.model.Supplement;
import com.juju.cozyformombackend3.domain.userlog.supplement.repository.SupplementRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SupplementService {

	private final SupplementRepository supplementRepository;
	private final UserRepository userRepository;

	@Transactional
	public RegisterSupplementResponse registerSupplement(Long userId, RegisterSupplementRequest request) {
		User user = findByUserId(userId);
		if (supplementRepository.existsBySupplementName(request.getSupplementName())) {
			throw new IllegalArgumentException("이미 존재하는 보충제 이름입니다.");
		}
		Supplement savedSupplement = supplementRepository.save(Supplement.builder()
			.user(user)
			.supplementName(request.getSupplementName())
			.targetCount(request.getTargetCount())
			.build());
		user.registerSupplement(savedSupplement);

		return RegisterSupplementResponse.of(savedSupplement.getSupplementId());
	}

	@Transactional
	public UpdateSupplementResponse updateSupplement(Long supplementId, UpdateSupplementRequest request) {
		Supplement findSupplement = supplementRepository.findById(supplementId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 영양제 입니다."));
		findSupplement.update(request);

		return UpdateSupplementResponse.of(findSupplement.getSupplementId());
	}

	private User findByUserId(Long userId) {
		return userRepository.findByUserId(userId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
	}
}
