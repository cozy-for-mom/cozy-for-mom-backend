package com.juju.cozyformombackend3.domain.bloodsugar.service;

import com.juju.cozyformombackend3.domain.bloodsugar.dto.request.ModifyBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.request.SaveBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.response.ModifyBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.response.SaveBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.bloodsugar.model.BloodSugarRecord;
import com.juju.cozyformombackend3.domain.bloodsugar.repository.BloodSugarRepository;
import com.juju.cozyformombackend3.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public ModifyBloodSugarRecordResponse updateBloodSugarRecord(ModifyBloodSugarRecordRequest request, User user) {
		BloodSugarRecord modifiedRecord = bloodSugarRepository.findById(request.getId())
						.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 혈당 기록입니다."));
		modifiedRecord.update(request.getDate(), request.getType(), request.getLevel());
		return ModifyBloodSugarRecordResponse.of(modifiedRecord.getBloodSugarId());
	}
}
