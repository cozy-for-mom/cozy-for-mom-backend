package com.juju.cozyformombackend3.domain.userlog.weight.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.user.error.UserErrorCode;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.object.FindPeriodicWeight;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.request.RecordWeightRequest;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.request.UpdateWeightRequest;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.response.FindWeightListResponse;
import com.juju.cozyformombackend3.domain.userlog.weight.exception.WeightErrorCode;
import com.juju.cozyformombackend3.domain.userlog.weight.model.WeightRecord;
import com.juju.cozyformombackend3.domain.userlog.weight.repository.WeightRepository;
import com.juju.cozyformombackend3.global.dto.request.FindPeriodRecordCondition;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
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
	public void updateWeight(Long userId, LocalDate date, UpdateWeightRequest request) {
		User user = findUserById(userId);
		WeightRecord weightRecord = weightRepository.findByUserAndRecordDate(user, date)
			.orElseThrow(() -> new BusinessException(WeightErrorCode.RECORD_DATE_NOT_FOUND));
		weightRecord.updateWeight(request.getWeight());
	}

	@Transactional
	public void deleteWeight(Long userId, LocalDate date) {
		User user = findUserById(userId);
		WeightRecord weightRecord = weightRepository.findByUserAndRecordDate(user, date)
			.orElseThrow(() -> new BusinessException(WeightErrorCode.RECORD_DATE_NOT_FOUND));
		weightRepository.delete(weightRecord);
	}

	public FindWeightListResponse findWeight(FindPeriodRecordCondition condition) {
		User user = findUserById(condition.getUserId());
		WeightRecord weightRecord = weightRepository.findByUserAndRecordDate(user, condition.getDate())
			.orElse(WeightRecord.builder().user(user).weight(0d).recordDate(condition.getDate()).build());
		List<FindPeriodicWeight> findPeriodicWeights = weightRepository.findPeriodRecordByDate(condition);

		return FindWeightListResponse.of(condition.getType(), weightRecord.getWeight(), findPeriodicWeights);
	}

	private User findUserById(Long userId) {
		return userRepository.findByUserId(userId)
			.orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));
	}
}
