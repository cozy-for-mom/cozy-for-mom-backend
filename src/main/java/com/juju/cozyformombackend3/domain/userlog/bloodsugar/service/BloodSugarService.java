package com.juju.cozyformombackend3.domain.userlog.bloodsugar.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.user.error.UserErrorCode;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.controller.dto.FindBloodSugarListResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.controller.dto.FindDailyBloodSugarListResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.controller.dto.ModifyBloodSugarRecord;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.controller.dto.SaveBloodSugarRecord;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodicBloodSugar;
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
    public SaveBloodSugarRecord.Response saveBloodSugarRecord(SaveBloodSugarRecord.Request request, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));

        if (bloodSugarRepository.existsByUserIdAndRecordAtAndBloodSugarRecordType(
            user.getId(), request.getDate(), request.getType())) {
            throw new BusinessException(BloodSugarErrorCode.CONFLICT_ALREADY_EXIST_RECORD);
        }
        BloodSugarRecord savedRecord = bloodSugarRepository.save(request.toBloodSugar(user));

        return SaveBloodSugarRecord.Response.of(savedRecord.getId());
    }

    @Transactional
    public ModifyBloodSugarRecord.Response updateBloodSugarRecord(Long recordId, ModifyBloodSugarRecord.Request request,
        Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));

        BloodSugarRecord modifiedRecord = bloodSugarRepository.findById(recordId)
            .orElseThrow(() -> new BusinessException(BloodSugarErrorCode.NOT_FOUND_BLOODSUGAR_RECORD));
        if (modifiedRecord.getUser() != user) {
            throw new BusinessException(BloodSugarErrorCode.FORBIDDEN_NOT_YOUR_RESOURCE);
        }
        modifiedRecord.update(request.getDate(), request.getType(), request.getLevel());
        return ModifyBloodSugarRecord.Response.of(modifiedRecord.getId());
    }

    @Transactional
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
