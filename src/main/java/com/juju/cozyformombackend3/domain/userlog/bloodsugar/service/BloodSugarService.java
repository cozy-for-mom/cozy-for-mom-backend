package com.juju.cozyformombackend3.domain.userlog.bloodsugar.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodicBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.request.ModifyBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.request.SaveBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.FindBloodSugarListResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.FindDailyBloodSugarListResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.ModifyBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.SaveBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.model.BloodSugarRecord;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.repository.BloodSugarRepository;
import com.juju.cozyformombackend3.global.dto.request.FindPeriodRecordCondition;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BloodSugarService {

	private final BloodSugarRepository bloodSugarRepository;
	private final UserRepository userRepository;

	@Transactional
	public SaveBloodSugarRecordResponse saveBloodSugarRecord(SaveBloodSugarRecordRequest request, Long userId) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
		Long savedRecordId = user.addBloodSugarRecord(request.getDate(), request.getType(), request.getLevel());
		return SaveBloodSugarRecordResponse.of(savedRecordId);
	}

	@Transactional
	public ModifyBloodSugarRecordResponse updateBloodSugarRecord(Long recordId, ModifyBloodSugarRecordRequest request,
		Long userId) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

		BloodSugarRecord modifiedRecord = bloodSugarRepository.findById(recordId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 혈당 기록입니다."));
		if (modifiedRecord.getUser() != user) {
			throw new IllegalArgumentException("본인의 혈당 기록만 수정할 수 있습니다.");
		}
		modifiedRecord.update(request.getDate(), request.getType(), request.getLevel());
		return ModifyBloodSugarRecordResponse.of(modifiedRecord.getBloodSugarId());
	}

	public void deleteBloodSugarRecord(Long recordId, Long userId) {
		User user = userRepository.findByUserId(userId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

		BloodSugarRecord deletedRecord = bloodSugarRepository.findById(recordId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 혈당 기록입니다."));
		if (deletedRecord.getUser() != user) {
			throw new IllegalArgumentException("본인의 혈당 기록만 삭제할 수 있습니다.");
		}
		bloodSugarRepository.delete(deletedRecord);
	}

	public FindDailyBloodSugarListResponse findDailyBloodSugarRecord(Long userId, String date) {
		return FindDailyBloodSugarListResponse.of(bloodSugarRepository.searchAllByCreatedAt(userId, date));
	}

	public FindBloodSugarListResponse findBloodSugarRecord(FindPeriodRecordCondition condition) {
		List<FindPeriodicBloodSugar> findPeriodicBloodSugars = bloodSugarRepository.findPeriodRecordByDate(condition);

		return FindBloodSugarListResponse.of(condition.getType().name(), findPeriodicBloodSugars);
	}
}
