package com.juju.cozyformombackend3.global.dto.request;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RecordPeriod {
	DAILY("daily"),
	WEEKLY("weekly"),
	MONTHLY("monthly");

	private final String periodKeyword;

	public static RecordPeriod of(String periodKeyword) {
		if (periodKeyword.isBlank())
			return null;
		return Arrays.stream(values())
			.filter(keyword -> keyword.periodKeyword.equals(periodKeyword))
			.findFirst()
			.orElseThrow();
	}
}
