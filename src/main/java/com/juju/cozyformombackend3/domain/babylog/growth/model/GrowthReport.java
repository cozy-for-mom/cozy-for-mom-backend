package com.juju.cozyformombackend3.domain.babylog.growth.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.juju.cozyformombackend3.global.model.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "growth_report")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GrowthReport extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "record_date")
	private LocalDate recordDate;

	@OneToOne
	private GrowthDiary growthDiary;

	@OneToMany(mappedBy = "growthReport", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private List<GrowthRecord> growthRecordList = new ArrayList<>();

	@Builder
	private GrowthReport(LocalDate recordDate, GrowthDiary growthDiary) {
		this.recordDate = recordDate;
		this.growthDiary = growthDiary;
		growthDiary.updateGrowthReport(this);
	}

	public void updateGrowthRecord(GrowthRecord growthRecord) {
		this.growthRecordList.add(growthRecord);
		growthRecord.updateGrowthReport(this);
	}

	@Override
	public void delete() {

	}
}
