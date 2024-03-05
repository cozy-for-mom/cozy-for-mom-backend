package com.juju.cozyformombackend3.domain.userlog.supplement.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.request.UpdateSupplementRequest;
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

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "target_count", nullable = false)
	private Integer targetCount;

	@Builder
	public Supplement(User user, String name, Integer targetCount) {
		this.user = user;
		this.name = name;
		this.targetCount = targetCount;
	}

	@Override
	public void delete() {

	}

	public void addSupplementRecord(SupplementRecord record) {
		supplementRecordList.add(record);
	}

	public void deleteSupplementRecord(List<LocalDateTime> datetimeList) {
		for (LocalDateTime datetime : datetimeList) {
			supplementRecordList.removeIf(supplementRecord -> supplementRecord.getRecordAt().equals(datetime));
		}
	}

	public void update(UpdateSupplementRequest request) {
		this.name = request.getSupplementName();
		this.targetCount = request.getTargetCount();
	}
}
