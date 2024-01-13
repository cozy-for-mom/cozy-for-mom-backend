package com.juju.cozyformombackend3.domain.growth.dto.request;

import com.juju.cozyformombackend3.domain.baby.model.Baby;
import com.juju.cozyformombackend3.domain.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.growth.model.GrowthDiary;
import com.juju.cozyformombackend3.domain.growth.model.GrowthRecord;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SaveGrowthRequest {

	private Long babyProfileId;

	private LocalDate date;

	private String growthImageUrl;

	private String content;

	private List<BabyInfoRequest> babies;

	public GrowthDiary toGrowthDiary(BabyProfile babyProfile) {
		return GrowthDiary.of(babyProfile, date, growthImageUrl, content);
	}

	public GrowthRecord toGrowthRecord(Baby baby) {
		GrowthInfoRequest growthInfo = (babies.stream()
						.filter(babyInfo -> babyInfo.getBabyId().equals(baby.getBabyId()))
						.findFirst()
						.orElseThrow(() -> new IllegalArgumentException("해당 아이의 성장 정보가 없습니다.")))
						.getGrowthInfo();

		return GrowthRecord.of(baby, date, growthInfo.weight, growthInfo.headDiameter, growthInfo.headCircum,
						growthInfo.abdomenCircum, growthInfo.thighCircum);
	}

	@Getter
	private static class BabyInfoRequest {

		private Long babyId;

		private String babyName;

		private GrowthInfoRequest growthInfo;
	}

	private static class GrowthInfoRequest {

		private double weight;

		private double headDiameter;

		private double headCircum;

		private double abdomenCircum;

		private double thighCircum;
	}
}