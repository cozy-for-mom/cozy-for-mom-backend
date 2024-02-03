package com.juju.cozyformombackend3.domain.babylog.growth.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateGrowthResponse {

	private Long growthDiaryId;

	private List<Long> growthRecordIdList;

	public static UpdateGrowthResponse of(Long growthDiaryId, List<Long> growthRecordIdList) {
		return new UpdateGrowthResponse(growthDiaryId, growthRecordIdList);
	}
}
