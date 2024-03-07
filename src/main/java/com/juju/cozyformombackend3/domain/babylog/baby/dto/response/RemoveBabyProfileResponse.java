package com.juju.cozyformombackend3.domain.babylog.baby.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RemoveBabyProfileResponse {
	private final Long babyProfileId;
	private final List<Long> babyIds;

	public static RemoveBabyProfileResponse of(final Long babyProfileId, List<Long> babyIds) {
		return new RemoveBabyProfileResponse(babyProfileId, babyIds);
	}
}
