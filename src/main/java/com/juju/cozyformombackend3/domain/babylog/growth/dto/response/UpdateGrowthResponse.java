package com.juju.cozyformombackend3.domain.babylog.growth.dto.response;

import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthReport;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateGrowthResponse {
	private Long growthReportId;
	private Long growthDiaryId;
	private List<Long> growthRecordIdList;

	public static UpdateGrowthResponse of(GrowthReport report) {
		return new UpdateGrowthResponse(report.getId(), report.getGrowthDiary().getId(),
			report.getGrowthRecordList()
				.stream()
				.map(growthRecord -> growthRecord.getId())
				.toList());
	}
}
