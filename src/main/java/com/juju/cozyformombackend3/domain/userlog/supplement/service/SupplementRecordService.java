package com.juju.cozyformombackend3.domain.userlog.supplement.service;

import static com.juju.cozyformombackend3.global.util.DateParser.*;

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
		Supplement supplement = supplementRepository.findBySupplementNameAndUser(request.getSupplementName(), user)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보충제입니다."));
		SupplementRecord supplementRecord = supplementRecordRepository.save(SupplementRecord.builder()
			.supplement(supplement)
			.recordAt(stringToLocalDateTime(request.getDatetime()))
			.build());

		return SaveSupplementRecordResponse.of(supplementRecord.getSupplementRecordId());
	}

	@Transactional
	public UpdateSupplementRecordResponse updateSupplementRecord(Long userId, Long recordId,
		UpdateSupplementRecordRequest request) {
		SupplementRecord findRecord = supplementRecordRepository.findById(recordId)
			.orElseThrow(() -> new IllegalArgumentException("등록되지 않은 기록입니다."));
		findRecord.update(stringToLocalDateTime(request.getDatetime()));

		return UpdateSupplementRecordResponse.of(findRecord.getSupplementRecordId());
	}

	@Transactional
	public void deleteSupplementRecord(Long userId, Long recordId) {
		// User user = findByUserId(userId);
		supplementRecordRepository.deleteById(recordId);
		//
		// for (DeleteSupplementRecordRequest deleteSupplementRecordRequest : request.getList()) {
		// 	Supplement supplement = supplementRepository.findBySupplementNameAndUser(
		// 			deleteSupplementRecordRequest.getSupplementName(), user)
		// 		.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보충제입니다."));
		// 	supplement.deleteSupplementRecord(deleteSupplementRecordRequest.getDatetimeList());
		// }
	}

	public GetDailySupplementResponse getSupplementRecord(long userId, String date) {
		List<FindDailySupplementIntake> findRecordList = supplementRecordRepository
			.findDailySupplementIntake(userId, date);

		return GetDailySupplementResponse.of(findRecordList);
	}

	private User findByUserId(Long userId) {
		return userRepository.findByUserId(userId)
			.orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));
	}

}
