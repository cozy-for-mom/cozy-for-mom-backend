package com.juju.cozyformombackend3.domain.userlog.supplement.model;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.model.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "supplement")
@Entity
public class Supplement extends BaseEntity {

	@OneToMany(mappedBy = "supplement", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private final List<SupplementRecord> supplementRecordList = new ArrayList<>();

	@Column(name = "supplement_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long supplementId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "supplement_name", nullable = false)
	private String supplementName;

	@Column(name = "target_count", nullable = false)
	private Integer targetCount;

	@Builder
	public Supplement(User user, String supplementName, Integer targetCount) {
		this.user = user;
		this.supplementName = supplementName;
		this.targetCount = targetCount;
	}

	@Override
	public void delete() {

	}

	public Long saveSupplementRecord(LocalDateTime datetime) {
		SupplementRecord supplementRecord = SupplementRecord.builder()
			.supplement(this)
			.recordAt(datetime)
			.build();
		supplementRecordList.add(supplementRecord);
		return supplementRecord.getSupplementRecordId();
	}

	public void deleteSupplementRecord(List<LocalDateTime> datetimeList) {
		for (LocalDateTime datetime : datetimeList) {
			supplementRecordList.removeIf(supplementRecord -> supplementRecord.getRecordAt().equals(datetime));
		}
	}
}
