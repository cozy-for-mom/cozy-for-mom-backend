package com.juju.cozyformombackend3.domain.growth.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveGrowthResponse {

	private Long growthDiaryId;

	private List<Long> growthRecordIdList;

	public static SaveGrowthResponse of(Long growthDiaryId, List<Long> growthRecordIdList) {
		return new SaveGrowthResponse(growthDiaryId, growthRecordIdList);
	}
}
