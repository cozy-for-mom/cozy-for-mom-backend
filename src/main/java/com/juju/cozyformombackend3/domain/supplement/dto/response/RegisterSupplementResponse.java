package com.juju.cozyformombackend3.domain.supplement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterSupplementResponse {

	private final Long supplementId;

	public static RegisterSupplementResponse of(Long supplementId) {
		return new RegisterSupplementResponse(supplementId);
	}
}
