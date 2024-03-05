package com.juju.cozyformombackend3.domain.babylog.growth.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.baby.model.Baby;
import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthDiary;
import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthRecord;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SaveGrowthRequest {

	private Long babyProfileId;
	private LocalDate date;
	private String growthImageUrl;
	private String title;
	private String content;
	private List<BabyInfoRequest> babies;

	public GrowthDiary toGrowthDiary(BabyProfile babyProfile) {
		return GrowthDiary.of(babyProfile, date, growthImageUrl, title, content);
	}

	public GrowthRecord toGrowthRecord(Baby baby) {
		System.out.println(baby.getName());
		GrowthInfoRequest growthInfo = (babies.stream()
			.filter(babyInfo -> babyInfo.getBabyId().equals(baby.getId()))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("해당 아이의 성장 정보가 없습니다.")))
			.getGrowthInfo();

		return GrowthRecord.of(baby, date, growthInfo.weight, growthInfo.headDiameter, growthInfo.headCircum,
			growthInfo.abdomenCircum, growthInfo.thighLength);
	}

	@Getter
	private static class BabyInfoRequest {
		private Long babyId;
		private String babyName;
		private GrowthInfoRequest growthInfo;
	}

	@Getter
	private static class GrowthInfoRequest {
		private double weight;
		private double headDiameter;
		private double headCircum;
		private double abdomenCircum;
		private double thighLength;
	}
}