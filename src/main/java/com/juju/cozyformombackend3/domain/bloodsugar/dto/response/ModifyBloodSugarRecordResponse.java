package com.juju.cozyformombackend3.domain.bloodsugar.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyBloodSugarRecordResponse {

	private Long bloodSugarRecordId;

	public static ModifyBloodSugarRecordResponse of(Long bloodSugarRecordId) {
		return new ModifyBloodSugarRecordResponse(bloodSugarRecordId);
	}
}
