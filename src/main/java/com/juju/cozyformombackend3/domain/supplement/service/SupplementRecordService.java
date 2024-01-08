package com.juju.cozyformombackend3.domain.supplement.service;

import com.juju.cozyformombackend3.domain.supplement.dto.request.DeleteSupplementRecordRequest;
import com.juju.cozyformombackend3.domain.supplement.dto.request.SaveSupplementRecordRequest;
import com.juju.cozyformombackend3.domain.supplement.dto.response.SaveSupplementRecordResponse;
import com.juju.cozyformombackend3.domain.supplement.model.Supplement;
import com.juju.cozyformombackend3.domain.supplement.repository.SupplementRecordRepository;
import com.juju.cozyformombackend3.domain.supplement.repository.SupplementRepository;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.dto.request.ListRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SupplementRecordService {

	private final SupplementRecordRepository supplementRecordRepository;
	private final SupplementRepository supplementRepository;

	@Transactional
	public SaveSupplementRecordResponse saveSupplementRecord(SaveSupplementRecordRequest request, User user) {
		Supplement supplement = supplementRepository.findBySupplementNameAndUser(request.getSupplementName(), user)
						.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보충제입니다."));
		Long savedRecordId = supplement.saveSupplementRecord(request.getDatetime());
		return SaveSupplementRecordResponse.of(savedRecordId);
	}

	@Transactional
	public void deleteSupplementRecord(ListRequest<DeleteSupplementRecordRequest> request, User user) {
		for (DeleteSupplementRecordRequest deleteSupplementRecordRequest : request.getList()) {
			Supplement supplement = supplementRepository.findBySupplementNameAndUser(
											deleteSupplementRecordRequest.getSupplementName(), user)
							.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보충제입니다."));
			supplement.deleteSupplementRecord(deleteSupplementRecordRequest.getDatetimeList());
		}
	}
}
