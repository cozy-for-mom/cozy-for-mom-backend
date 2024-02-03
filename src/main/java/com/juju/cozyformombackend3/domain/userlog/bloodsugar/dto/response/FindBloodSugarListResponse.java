package com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response;

import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodicBloodSugar;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.data.domain.Slice;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FindBloodSugarListResponse {
	private final String type;
	private final Slice<FindPeriodicBloodSugar> bloodsugars;

	public static FindBloodSugarListResponse of(String type, Slice<FindPeriodicBloodSugar> bloodsugars) {
		return new FindBloodSugarListResponse(type, bloodsugars);
	}
}
