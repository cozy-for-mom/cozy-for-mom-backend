package com.juju.cozyformombackend3.domain.userlog.bloodsugar.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.user.error.UserErrorCode;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodicBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.request.ModifyBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.request.SaveBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.FindBloodSugarListResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.FindDailyBloodSugarListResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.ModifyBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.SaveBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.error.BloodSugarErrorCode;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.model.BloodSugarRecord;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.repository.BloodSugarRepository;
import com.juju.cozyformombackend3.global.dto.request.FindPeriodRecordCondition;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BloodSugarService {

    private final BloodSugarRepository bloodSugarRepository;
    private final UserRepository userRepository;

    @Transactional
    public SaveBloodSugarRecordResponse saveBloodSugarRecord(SaveBloodSugarRecordRequest request, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));

        if (bloodSugarRepository.existsByUserIdAndRecordAtAndBloodSugarRecordType(
            user.getId(), request.getDate(), request.getType())) {
            throw new BusinessException(BloodSugarErrorCode.CONFLICT_ALREADY_EXIST_RECORD);
        }
        BloodSugarRecord savedRecord = bloodSugarRepository.save(request.toBloodSugar(user));

        return SaveBloodSugarRecordResponse.of(savedRecord.getId());
    }

    @Transactional
    public ModifyBloodSugarRecordResponse updateBloodSugarRecord(Long recordId, ModifyBloodSugarRecordRequest request,
        Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));

        BloodSugarRecord modifiedRecord = bloodSugarRepository.findById(recordId)
            .orElseThrow(() -> new BusinessException(BloodSugarErrorCode.NOT_FOUND_BLOODSUGAR_RECORD));
        if (modifiedRecord.getUser() != user) {
            throw new BusinessException(BloodSugarErrorCode.FORBIDDEN_NOT_YOUR_RESOURCE);
        }
        modifiedRecord.update(request.getDate(), request.getType(), request.getLevel());
        return ModifyBloodSugarRecordResponse.of(modifiedRecord.getId());
    }

    public void deleteBloodSugarRecord(Long recordId, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));

        BloodSugarRecord deletedRecord = bloodSugarRepository.findById(recordId)
            .orElseThrow(() -> new BusinessException(BloodSugarErrorCode.NOT_FOUND_BLOODSUGAR_RECORD));
        if (deletedRecord.getUser() != user) {
            throw new BusinessException(BloodSugarErrorCode.FORBIDDEN_NOT_YOUR_RESOURCE);
        }
        bloodSugarRepository.delete(deletedRecord);
    }

    public FindDailyBloodSugarListResponse findDailyBloodSugarRecord(Long userId, LocalDate date) {
        return FindDailyBloodSugarListResponse.of(bloodSugarRepository.searchAllByRecordAt(userId, date));
    }

    public FindBloodSugarListResponse findBloodSugarRecord(FindPeriodRecordCondition condition) {
        List<FindPeriodicBloodSugar> findPeriodicBloodSugars = bloodSugarRepository.findPeriodRecordByDate(condition);

        return FindBloodSugarListResponse.of(condition.getType().getPeriodKeyword(), findPeriodicBloodSugars);
    }
}
