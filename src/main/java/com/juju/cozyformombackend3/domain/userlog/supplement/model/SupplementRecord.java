package com.juju.cozyformombackend3.domain.userlog.supplement.model;

import java.time.LocalDateTime;

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

@Getter
@NoArgsConstructor
@Table(name = "supplement_record")
@Entity
public class SupplementRecord extends BaseEntity {

	@Column(name = "supplement_record_id", nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long supplementRecordId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "supplement_id")
	private Supplement supplement;

	private LocalDateTime recordAt;

	@Builder
	public SupplementRecord(Supplement supplement, LocalDateTime recordAt) {
		this.supplement = supplement;
		this.recordAt = recordAt;
	}

	public void update(LocalDateTime recordAt) {
		this.recordAt = recordAt;
	}

	@Override
	public void delete() {

	}
}
