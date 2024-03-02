package com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object;

import com.juju.cozyformombackend3.global.dto.request.RecordPeriod;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindPeriodBloodSugarCondition {
	private Long userId;
	private String date;
	private RecordPeriod type;
	private Long size;

	public static FindPeriodBloodSugarCondition of(Long userId, String date, RecordPeriod type, Long size) {
		return new FindPeriodBloodSugarCondition(userId, date, type, size);
	}
}
