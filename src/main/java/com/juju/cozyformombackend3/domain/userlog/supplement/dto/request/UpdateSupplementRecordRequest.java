package com.juju.cozyformombackend3.domain.userlog.supplement.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateSupplementRecordRequest {
	private String supplementName;
	private String datetime;
}
