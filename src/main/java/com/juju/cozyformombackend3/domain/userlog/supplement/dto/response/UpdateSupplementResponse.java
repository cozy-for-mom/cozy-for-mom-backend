package com.juju.cozyformombackend3.domain.userlog.supplement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateSupplementResponse {
	private final Long supplementId;

	public static UpdateSupplementResponse of(Long supplementId) {
		return new UpdateSupplementResponse(supplementId);
	}
}

