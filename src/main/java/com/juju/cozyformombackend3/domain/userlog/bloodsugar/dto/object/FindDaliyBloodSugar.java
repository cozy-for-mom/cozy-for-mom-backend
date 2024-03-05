package com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object;

import com.juju.cozyformombackend3.domain.userlog.bloodsugar.model.BloodSugarRecordType;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class FindDaliyBloodSugar {
	private final Long id;
	private final String type;
	private final int level;

	@QueryProjection
	public FindDaliyBloodSugar(Long id, BloodSugarRecordType type, int level) {
		this.id = id;
		this.type = type.getDescription();
		this.level = level;
	}
}
