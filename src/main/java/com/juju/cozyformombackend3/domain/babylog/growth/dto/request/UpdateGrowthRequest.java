package com.juju.cozyformombackend3.domain.babylog.growth.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.baby.error.BabyErrorCode;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateGrowthRequest {

	private Long growthDiaryId;
	private Long babyProfileId;
	private LocalDate date;
	private String growthImageUrl;
	private String title;
	private String content;
	private List<BabyInfoRequest> babies;

	public BabyInfoRequest getBabyInfo(Long growthRecordId) {
		return babies.stream()
			.filter(babyInfo -> babyInfo.getGrowthRecordId().equals(growthRecordId))
			.findFirst()
			.orElseThrow(() -> new BusinessException(BabyErrorCode.NOT_FOUND_BABY));
	}

	public GrowthDiaryDto getGrowthDiaryDto() {
		return GrowthDiaryDto.of(growthDiaryId, babyProfileId, date, growthImageUrl, title, content);
	}

	@Getter
	public static class BabyInfoRequest {

		private Long growthRecordId;

		private Long babyId;

		private String babyName;

		private GrowthInfoRequest growthInfo;
	}

	@Getter
	public static class GrowthInfoRequest {

		private double weight;

		private double headDiameter;

		private double headCircum;

		private double abdomenCircum;

		private double thighLength;
	}

	@Getter
	@AllArgsConstructor(staticName = "of")
	// TODO: 이너클래스 공브하기
	public static class GrowthDiaryDto {

		private Long growthDiaryId;

		private Long babyProfileId;

		private LocalDate date;

		private String growthImageUrl;

		private String title;

		private String content;
	}
}
