package com.juju.cozyformombackend3.domain.bloodsugar.model;

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
import lombok.Builder;
import lombok.NoArgsConstructor;

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

	@Builder
	public BloodSugarRecord(User user, Double level, BloodSugarRecordType bloodSugarRecordType) {
		this.user = user;
		this.level = level;
		this.bloodSugarRecordType = bloodSugarRecordType;
	}

	@Override
	public void delete() {

	}
}
