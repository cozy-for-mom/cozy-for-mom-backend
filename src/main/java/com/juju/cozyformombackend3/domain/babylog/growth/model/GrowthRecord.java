package com.juju.cozyformombackend3.domain.babylog.growth.model;

import java.time.LocalDate;

import com.juju.cozyformombackend3.domain.babylog.baby.model.Baby;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.request.UpdateGrowthRequest.BabyInfoRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "growth_record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GrowthRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "growth_record_id")
	private Long growthRecordId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "baby_id")
	private Baby baby;

	@ManyToOne()
	@JoinColumn(name = "id")
	private GrowthReport growthReport;

	@Column(name = "record_at", nullable = false)
	private LocalDate recordAt;

	@Column(name = "weight")
	private Double weight;

	@Column(name = "head_diameter")
	private Double headDiameter;

	@Column(name = "head_circum")
	private Double headCircum;

	@Column(name = "abdomen_circum")
	private Double abdomenCircum;

	@Column(name = "thigh_length")
	private Double thighLength;

	private GrowthRecord(Baby baby, LocalDate recordAt, Double weight, Double headDiameter, Double headCircum,
		Double abdomenCircum, Double thighLength) {
		this.baby = baby;
		this.recordAt = recordAt;
		this.weight = weight;
		this.headDiameter = headDiameter;
		this.headCircum = headCircum;
		this.abdomenCircum = abdomenCircum;
		this.thighLength = thighLength;
	}

	public static GrowthRecord of(Baby baby, LocalDate recordAt, Double weight, Double headDiameter, Double headCircum,
		Double abdomenCircum, Double thighLength) {
		return new GrowthRecord(baby, recordAt, weight, headDiameter, headCircum, abdomenCircum, thighLength);
	}

	public void update(BabyInfoRequest baby) {
		this.weight = baby.getGrowthInfo().getWeight();
		this.headDiameter = baby.getGrowthInfo().getHeadDiameter();
		this.headCircum = baby.getGrowthInfo().getHeadCircum();
		this.abdomenCircum = baby.getGrowthInfo().getAbdomenCircum();
		this.thighLength = baby.getGrowthInfo().getThighLength();
	}

	public void updateGrowthReport(GrowthReport growthReport) {
		this.growthReport = growthReport;
	}
}
