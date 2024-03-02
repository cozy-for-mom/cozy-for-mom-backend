package com.juju.cozyformombackend3.domain.userlog.supplement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class UpdateSupplementRecordResponse {
	private final Long supplementRecordId;

	public static UpdateSupplementRecordResponse of(Long supplementRecordId) {
		return new UpdateSupplementRecordResponse(supplementRecordId);
	}
}