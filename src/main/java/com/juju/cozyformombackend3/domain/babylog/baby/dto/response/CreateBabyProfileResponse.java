package com.juju.cozyformombackend3.domain.babylog.baby.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateBabyProfileResponse {
	private final Long babyProfileId;
	private final List<Long> babyIds;

	public static CreateBabyProfileResponse of(final Long babyProfileId, List<Long> babyIds) {
		return new CreateBabyProfileResponse(babyProfileId, babyIds);
	}
}
