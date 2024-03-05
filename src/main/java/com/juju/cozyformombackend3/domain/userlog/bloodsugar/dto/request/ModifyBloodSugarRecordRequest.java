package com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.request;

import java.time.LocalDate;

import com.juju.cozyformombackend3.domain.userlog.bloodsugar.model.BloodSugarRecordType;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModifyBloodSugarRecordRequest {

	private LocalDate date;
	private String type;
	private int level;

	public BloodSugarRecordType getType() {
		return BloodSugarRecordType.ofDescription(type);
	}
}
