package com.juju.cozyformombackend3.domain.userlog.weight.dto.object;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class FindPeriodicWeight {
	private final String endDate;
	private final double weight;

	@QueryProjection
	public FindPeriodicWeight(String endDate, double weight) {
		this.endDate = endDate;
		this.weight = weight;
	}
}
