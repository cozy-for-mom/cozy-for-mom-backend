package com.juju.cozyformombackend3.domain.supplement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveSupplementRecordResponse {

	private final Long supplementRecordId;

	public static SaveSupplementRecordResponse of(Long supplementRecordId) {
		return new SaveSupplementRecordResponse(supplementRecordId);
	}
}
