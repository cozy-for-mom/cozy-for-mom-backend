package com.juju.cozyformombackend3.domain.babylog.growth.model;

import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.request.UpdateGrowthRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "growth_diary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GrowthDiary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "growth_diary_id")
	private Long growthDiaryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "baby_profile_id")
	private BabyProfile babyProfile;

	@Column(name = "record_at", nullable = false)
	private LocalDate recordAt;

	@Column(name = "growth_image_url", columnDefinition = "TEXT")
	private String growthImageUrl;

	@Column(name = "content", columnDefinition = "TEXT")
	private String content;

	private GrowthDiary(BabyProfile babyProfile, LocalDate recordAt, String growthImageUrl, String content) {
		this.babyProfile = babyProfile;
		this.recordAt = recordAt;
		this.growthImageUrl = growthImageUrl;
		this.content = content;
	}

	public static GrowthDiary of(BabyProfile babyProfile, LocalDate recordAt, String growthImageUrl, String content) {
		return new GrowthDiary(babyProfile, recordAt, growthImageUrl, content);
	}

	public void update(UpdateGrowthRequest.GrowthDiaryDto growthDiaryDto) {
		this.recordAt = growthDiaryDto.getDate();
		this.growthImageUrl = growthDiaryDto.getGrowthImageUrl();
		this.content = growthDiaryDto.getContent();
	}
}