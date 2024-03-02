package com.juju.cozyformombackend3.domain.userlog.bloodsugar.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodBloodSugarCondition;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodicBloodSugar;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.request.ModifyBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.request.SaveBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.FindBloodSugarListResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.FindDailyBloodSugarListResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.ModifyBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.SaveBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.model.BloodSugarRecord;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.repository.BloodSugarRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BloodSugarService {

	private final BloodSugarRepository bloodSugarRepository;

	@Transactional
	public SaveBloodSugarRecordResponse saveBloodSugarRecord(SaveBloodSugarRecordRequest request, User user) {
		Long savedRecordId = user.addBloodSugarRecord(request.getDate(), request.getType(), request.getLevel());
		return SaveBloodSugarRecordResponse.of(savedRecordId);
	}

	@Transactional
	public ModifyBloodSugarRecordResponse updateBloodSugarRecord(Long recordId, ModifyBloodSugarRecordRequest request,
		User user) {
		BloodSugarRecord modifiedRecord = bloodSugarRepository.findById(recordId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 혈당 기록입니다."));
		if (modifiedRecord.getUser() != user) {
			throw new IllegalArgumentException("본인의 혈당 기록만 수정할 수 있습니다.");
		}
		modifiedRecord.update(request.getDate(), request.getType(), request.getLevel());
		return ModifyBloodSugarRecordResponse.of(modifiedRecord.getBloodSugarId());
	}

	public void deleteBloodSugarRecord(Long recordId, User user) {
		BloodSugarRecord deletedRecord = bloodSugarRepository.findById(recordId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 혈당 기록입니다."));
		if (deletedRecord.getUser() != user) {
			throw new IllegalArgumentException("본인의 혈당 기록만 삭제할 수 있습니다.");
		}
		bloodSugarRepository.delete(deletedRecord);
	}

	public FindDailyBloodSugarListResponse findDailyBloodSugarRecord(long userId, String date) {
		return FindDailyBloodSugarListResponse.of(bloodSugarRepository.searchAllByCreatedAt(userId, date));
	}

	public FindBloodSugarListResponse findBloodSugarRecord(FindPeriodBloodSugarCondition condition) {
		List<FindPeriodicBloodSugar> findPeriodicBloodSugars = bloodSugarRepository.findPeriodRecordByDate(condition);

		return FindBloodSugarListResponse.of(condition.getType().name(), findPeriodicBloodSugars);
	}
}
