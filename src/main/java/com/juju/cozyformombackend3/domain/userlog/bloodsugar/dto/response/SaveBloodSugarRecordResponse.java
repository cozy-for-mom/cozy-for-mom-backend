package com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveBloodSugarRecordResponse {

	private Long bloodSugarRecordId;

	public static SaveBloodSugarRecordResponse of(Long bloodSugarRecordId) {
		return new SaveBloodSugarRecordResponse(bloodSugarRecordId);
	}
}
