package com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class FindPeriodicBloodSugar {
	private final String endDate;
	private final double averageLevel;

	@QueryProjection
	public FindPeriodicBloodSugar(String endDate, double averageLevel) {
		this.endDate = endDate;
		this.averageLevel = averageLevel;
	}
}
