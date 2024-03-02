package com.juju.cozyformombackend3.domain.userlog.supplement.dto.object;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class FindDailySupplementIntake {
	private final Long supplementId;
	private final String supplementName;
	private final int targetCount;
	private final Long recordId;
	private final LocalDateTime datetime;

	@QueryProjection
	public FindDailySupplementIntake(Long supplementId, String supplementName, int targetCount,
		Long recordId, LocalDateTime datetime) {
		this.supplementId = supplementId;
		this.supplementName = supplementName;
		this.targetCount = targetCount;
		this.recordId = recordId;
		this.datetime = datetime;
	}
}
