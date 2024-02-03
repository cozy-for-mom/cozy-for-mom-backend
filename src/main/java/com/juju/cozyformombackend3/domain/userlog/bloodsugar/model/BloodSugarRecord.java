package com.juju.cozyformombackend3.domain.userlog.bloodsugar.model;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "blood_sugar_record")
@Entity
public class BloodSugarRecord extends BaseEntity {

	@Column(name = "blood_sugar_id", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bloodSugarId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "level", nullable = false)
	private Double level;

	@Column(name = "blood_sugar_record_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private BloodSugarRecordType bloodSugarRecordType;

	private LocalDate recordAt;

	@Builder
	public BloodSugarRecord(User user, Double level, BloodSugarRecordType bloodSugarRecordType, LocalDate recordAt) {
		this.user = user;
		this.level = level;
		this.bloodSugarRecordType = bloodSugarRecordType;
		this.recordAt = recordAt;
	}

	@Override
	public void delete() {

	}

	public void update(LocalDate date, BloodSugarRecordType type, double level) {
		this.recordAt = date;
		this.bloodSugarRecordType = type;
		this.level = level;
	}
}
