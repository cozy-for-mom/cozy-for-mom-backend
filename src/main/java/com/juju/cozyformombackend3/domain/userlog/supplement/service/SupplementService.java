package com.juju.cozyformombackend3.domain.userlog.supplement.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.user.error.UserErrorCode;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.request.RegisterSupplementRequest;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.request.UpdateSupplementRequest;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.response.RegisterSupplementResponse;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.response.UpdateSupplementResponse;
import com.juju.cozyformombackend3.domain.userlog.supplement.error.SupplementErrorCode;
import com.juju.cozyformombackend3.domain.userlog.supplement.model.Supplement;
import com.juju.cozyformombackend3.domain.userlog.supplement.repository.SupplementRepository;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class SupplementService {

	private final SupplementRepository supplementRepository;
	private final UserRepository userRepository;

	@Transactional
	public RegisterSupplementResponse registerSupplement(Long userId, RegisterSupplementRequest request) {
		User user = findByUserId(userId);
		if (supplementRepository.existsBySupplementName(request.getSupplementName())) {
			throw new BusinessException(SupplementErrorCode.CONFLICT_ALREADY_REGISTER_SUPPLEMENT);
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
			.orElseThrow(() -> new BusinessException(SupplementErrorCode.NOT_FOUND_SUPPLEMENT));
		findSupplement.update(request);

		return UpdateSupplementResponse.of(findSupplement.getSupplementId());
	}

	@Transactional
	public void deleteSupplement(Long supplementId) {
		supplementRepository.deleteById(supplementId);
	}

	private User findByUserId(Long userId) {
		return userRepository.findByUserId(userId)
			.orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));
	}

}
