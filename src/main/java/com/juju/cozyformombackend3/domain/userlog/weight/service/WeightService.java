package com.juju.cozyformombackend3.domain.userlog.weight.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.user.error.UserErrorCode;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.request.DeleteWeightRequest;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.request.RecordWeightRequest;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.response.GetWeightListResponse;
import com.juju.cozyformombackend3.domain.userlog.weight.exception.WeightErrorCode;
import com.juju.cozyformombackend3.domain.userlog.weight.model.WeightRecord;
import com.juju.cozyformombackend3.domain.userlog.weight.repository.WeightRepository;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class WeightService {

	private final WeightRepository weightRepository;
	private final UserRepository userRepository;

	@Transactional
	public void recordWeight(Long userId, RecordWeightRequest request) {
		User user = findUserById(userId);
		if (weightRepository.existsByUserAndRecordDate(user, request.getDate())) {
			throw new BusinessException(WeightErrorCode.RECORD_DATE_ALREADY_EXISTS);
		}
		weightRepository.save(request.toEntity(user));
	}

	@Transactional
	public void updateWeight(Long userId, RecordWeightRequest request) {
		User user = findUserById(userId);
		WeightRecord weightRecord = weightRepository.findByUserAndRecordDate(user, request.getDate())
			.orElseThrow(() -> new BusinessException(WeightErrorCode.RECORD_DATE_NOT_FOUND));
		weightRecord.updateWeight(request.getWeight());
	}

	@Transactional
	public void deleteWeight(Long userId, DeleteWeightRequest request) {
		User user = findUserById(userId);
		WeightRecord weightRecord = weightRepository.findByUserAndRecordDate(user, request.getDate())
			.orElseThrow(() -> new BusinessException(WeightErrorCode.RECORD_DATE_NOT_FOUND));
		weightRepository.delete(weightRecord);
	}

	public GetWeightListResponse getWeight(long userId, LocalDate date, String type) {

		return null;
	}

	private User findUserById(Long userId) {
		return userRepository.findByUserId(userId)
			.orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));
	}
}
