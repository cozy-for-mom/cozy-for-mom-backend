package com.juju.cozyformombackend3.domain.growth.model;

import com.juju.cozyformombackend3.domain.baby.model.BabyProfile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "growth_diary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GrowthDiary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "growth_diary_id")
	private Long growthDiaryId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "baby_profile_id")
	private BabyProfile babyProfile;

	@Column(name = "record_at", nullable = false)
	private LocalDateTime recordAt;

	@Column(name = "growth_image_url", columnDefinition = "TEXT")
	private String growthImageUrl;

	@Column(name = "content", columnDefinition = "TEXT")
	private String content;
}
