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
import com.juju.cozyformombackend3.domain.userlog.weight.dto.response.RecordWeightResponse;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.response.UpdateWeightResponse;
import com.juju.cozyformombackend3.domain.userlog.weight.error.WeightErrorCode;
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
    public RecordWeightResponse recordWeight(Long userId, RecordWeightRequest request) {
        User user = findUserById(userId);

        if (weightRepository.existsByUserIdAndRecordAt(userId, request.getDate())) {
            throw new BusinessException(WeightErrorCode.RECORD_DATE_ALREADY_EXISTS);
        }
        WeightRecord savedRecord = weightRepository.save(request.toWeightRecord(user));

        return RecordWeightResponse.of(savedRecord.getId());
    }

    @Transactional
    public UpdateWeightResponse updateWeight(Long userId, LocalDate date, UpdateWeightRequest request) {
        User user = findUserById(userId);
        WeightRecord weightRecord = weightRepository.findByUserAndRecordAt(user, date)
            .orElseThrow(() -> new BusinessException(WeightErrorCode.RECORD_DATE_NOT_FOUND));
        weightRecord.updateWeight(request.getWeight());

        return UpdateWeightResponse.of(weightRecord.getId());
    }

    @Transactional
    public void deleteWeight(Long userId, LocalDate date) {
        User user = findUserById(userId);
        WeightRecord weightRecord = weightRepository.findByUserAndRecordAt(user, date)
            .orElseThrow(() -> new BusinessException(WeightErrorCode.RECORD_DATE_NOT_FOUND));
        weightRepository.delete(weightRecord);
    }

    public FindWeightListResponse findWeight(FindPeriodRecordCondition condition) {
        User user = findUserById(condition.getUserId());
        // TODO 쿼리 합치기
        WeightRecord lastWeightRecord = weightRepository.findFirstByUserIdOrderByRecordAtDesc(condition.getUserId());

        WeightRecord weightRecord = weightRepository.findByUserAndRecordAt(user, LocalDate.now())
            .orElse(WeightRecord.builder().user(user).weight(0d).recordAt(condition.getDate()).build());
        
        List<FindPeriodicWeight> findPeriodicWeights = weightRepository.findPeriodRecordByDate(condition);

        return FindWeightListResponse.of(condition.getType(), weightRecord.getWeight(), lastWeightRecord.getRecordAt(),
            findPeriodicWeights);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));
    }
}
