package com.juju.cozyformombackend3.domain.supplement.service;

import com.juju.cozyformombackend3.domain.supplement.dto.request.RegisterSupplementRequest;
import com.juju.cozyformombackend3.domain.supplement.dto.response.RegisterSupplementResponse;
import com.juju.cozyformombackend3.domain.supplement.repository.SupplementRepository;
import com.juju.cozyformombackend3.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SupplementService {

	private final SupplementRepository supplementRepository;

	@Transactional
	public RegisterSupplementResponse registerSupplement(RegisterSupplementRequest request, User user) {
		if (supplementRepository.existsBySupplementName(request.getSupplementName())) {
			throw new IllegalArgumentException("이미 존재하는 보충제 이름입니다.");
		} else {
			return RegisterSupplementResponse.of(
							user.registerSupplement(request.getSupplementName(), request.getTargetCount()));
		}
	}
}
