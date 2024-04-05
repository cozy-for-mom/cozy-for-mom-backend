package com.juju.cozyformombackend3.domain.userlog.supplement.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.user.error.UserErrorCode;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.object.FindDailySupplementIntake;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.request.SaveSupplementRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.request.UpdateSupplementRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.response.GetDailySupplementResponse;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.response.SaveSupplementRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.response.UpdateSupplementRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.supplement.error.SupplementErrorCode;
import com.juju.cozyformombackend3.domain.userlog.supplement.model.Supplement;
import com.juju.cozyformombackend3.domain.userlog.supplement.model.SupplementRecord;
import com.juju.cozyformombackend3.domain.userlog.supplement.repository.SupplementRecordRepository;
import com.juju.cozyformombackend3.domain.userlog.supplement.repository.SupplementRepository;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SupplementRecordService {

    private final SupplementRecordRepository supplementRecordRepository;
    private final SupplementRepository supplementRepository;
    private final UserRepository userRepository;

    @Transactional
    public SaveSupplementRecordResponse saveSupplementRecord(Long userId, SaveSupplementRecordRequest request) {
        User user = findByUserId(userId);
        Supplement supplement = supplementRepository.findByNameAndUser(request.getSupplementName(), user)
            .orElseThrow(() -> new BusinessException(SupplementErrorCode.NOT_FOUND_SUPPLEMENT));
        SupplementRecord supplementRecord = supplementRecordRepository.save(SupplementRecord.builder()
            .supplement(supplement)
            .recordAt(request.getRecordAt())
            .build());

        return SaveSupplementRecordResponse.of(supplementRecord.getId());
    }

    @Transactional
    public UpdateSupplementRecordResponse updateSupplementRecord(Long userId, Long recordId,
        UpdateSupplementRecordRequest request) {
        SupplementRecord findRecord = supplementRecordRepository.findById(recordId)
            .orElseThrow(() -> new BusinessException(SupplementErrorCode.NOT_FOUND_SUPPLEMENT_RECORD));
        findRecord.update(request.getRecordAt());

        return UpdateSupplementRecordResponse.of(findRecord.getId());
    }

    @Transactional
    public void deleteSupplementRecord(Long userId, Long recordId) {
        supplementRecordRepository.deleteById(recordId);
    }

    public GetDailySupplementResponse getSupplementRecord(long userId, String date) {
        final List<Supplement> findSupplementList = supplementRepository.findAllByUserId(userId);
        final List<FindDailySupplementIntake> findRecordList = supplementRecordRepository
            .findDailySupplementIntake(userId, date);

        return GetDailySupplementResponse.of(findSupplementList, findRecordList);
    }

    private User findByUserId(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));
    }

}
