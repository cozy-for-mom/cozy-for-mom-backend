package com.juju.cozyformombackend3.domain.userlog.weight.dto.response;

import java.util.List;

import com.juju.cozyformombackend3.domain.userlog.weight.dto.object.FindPeriodicWeight;
import com.juju.cozyformombackend3.global.dto.request.RecordPeriod;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindWeightListResponse {
	private String type;
	private Double todayWeight;
	private List<FindPeriodicWeight> weightList;

	public static FindWeightListResponse of(RecordPeriod type, Double todayWeight,
		List<FindPeriodicWeight> findPeriodicWeights) {
		return new FindWeightListResponse(type.getPeriodKeyword(), todayWeight, findPeriodicWeights);
	}
}
