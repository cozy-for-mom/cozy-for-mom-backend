package com.juju.cozyformombackend3.domain.supplement.model;

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
import lombok.NoArgsConstructor;

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

	@Builder
	public SupplementRecord(Supplement supplement) {
		this.supplement = supplement;
	}

	@Override
	public void delete() {

	}
}
