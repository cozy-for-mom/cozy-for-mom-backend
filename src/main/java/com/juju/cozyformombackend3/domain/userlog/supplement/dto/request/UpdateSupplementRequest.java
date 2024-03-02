package com.juju.cozyformombackend3.domain.userlog.supplement.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateSupplementRequest {
	private String supplementName;
	private int targetCount;
}
