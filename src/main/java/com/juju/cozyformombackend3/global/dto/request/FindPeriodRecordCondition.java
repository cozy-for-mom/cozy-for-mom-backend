package com.juju.cozyformombackend3.global.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindPeriodRecordCondition {
	private Long userId;
	private String date;
	private RecordPeriod type;
	private Long size;

	public static FindPeriodRecordCondition of(Long userId, String date, RecordPeriod type, Long size) {
		return new FindPeriodRecordCondition(userId, date, type, size);
	}
}
