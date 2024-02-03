package com.juju.cozyformombackend3.domain.userlog.weight.service;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.request.DeleteWeightRequest;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.response.GetWeightListResponse;
import com.juju.cozyformombackend3.domain.userlog.weight.model.WeightRecord;
import com.juju.cozyformombackend3.domain.userlog.weight.repository.WeightRepository;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.request.RecordWeightRequest;
import com.juju.cozyformombackend3.domain.userlog.weight.exception.WeightErrorCode;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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

	@Transactional
	public void deleteWeight(DeleteWeightRequest request) {
		User user = null; // security context 가져오기
		WeightRecord weightRecord = weightRepository.findByUserAndRecordDate(user, request.getDate())
			.orElseThrow(() -> new BusinessException(WeightErrorCode.RECORD_DATE_NOT_FOUND));
		weightRepository.delete(weightRecord);
	}

	public GetWeightListResponse getWeight(long userId, LocalDate date, String type) {

		return null;
	}

	//	public WeightListResponse getWeight(LocalDate date, String type) {
	//	}
}
