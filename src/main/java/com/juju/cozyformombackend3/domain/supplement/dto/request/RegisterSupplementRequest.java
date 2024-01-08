package com.juju.cozyformombackend3.domain.supplement.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegisterSupplementRequest {

	private String supplementName;
	private int targetCount;
}
