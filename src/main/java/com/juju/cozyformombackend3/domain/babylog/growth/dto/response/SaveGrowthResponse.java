package com.juju.cozyformombackend3.domain.babylog.growth.dto.response;

import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthReport;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveGrowthResponse {

	private Long growthReportId;
	private Long growthDiaryId;
	private List<Long> growthRecordIdList;

	public static SaveGrowthResponse of(GrowthReport report) {
		return new SaveGrowthResponse(report.getId(), report.getGrowthDiary().getGrowthDiaryId(),
			report.getGrowthRecordList()
				.stream()
				.map(growthRecord -> growthRecord.getGrowthRecordId())
				.toList());
	}
}
