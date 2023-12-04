package com.juju.cozyformombackend3.domain.weight.service;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.weight.dto.request.RecordWeightRequest;
import com.juju.cozyformombackend3.domain.weight.exception.WeightErrorCode;
import com.juju.cozyformombackend3.domain.weight.model.WeightRecord;
import com.juju.cozyformombackend3.domain.weight.repository.WeightRepository;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class WeightService {

	private final WeightRepository weightRepository;

	@Transactional
	public void recordWeight(RecordWeightRequest request) {
		User user = null; // security context 가져오기
		if (weightRepository.existsByUserAndRecordDate(user, request.getDate())) {
			throw new BusinessException(WeightErrorCode.RECORD_DATE_ALREADY_EXISTS);
		}
		weightRepository.save(request.toEntity(user));
	}

	@Transactional
	public void updateWeight(RecordWeightRequest request) {
		User user = null; // security context 가져오기
		WeightRecord weightRecord = weightRepository.findByUserAndRecordDate(user, request.getDate())
						.orElseThrow(() -> new BusinessException(WeightErrorCode.RECORD_DATE_NOT_FOUND));
		weightRecord.updateWeight(request.getWeight());
	}
}
