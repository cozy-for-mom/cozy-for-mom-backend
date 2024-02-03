package com.juju.cozyformombackend3.domain.userlog.supplement.dto.request;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SaveSupplementRecordRequest {

	private String supplementName;
	private LocalDateTime datetime;
}
