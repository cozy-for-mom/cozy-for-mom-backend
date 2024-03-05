package com.juju.cozyformombackend3.domain.userlog.weight.model;

import java.time.LocalDate;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "weight_record")
@Getter
@NoArgsConstructor
public class WeightRecord extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "weight", nullable = false)
	private Double weight;

	@Column(name = "record_at", nullable = false)
	private LocalDate recordAt;

	@Builder
	public WeightRecord(User user, Double weight, LocalDate recordAt) {
		this.user = user;
		this.weight = weight;
		this.recordAt = recordAt;
	}

	@Override
	public void delete() {
	}

	public void updateWeight(double weight) {
		this.weight = weight;
	}
}
