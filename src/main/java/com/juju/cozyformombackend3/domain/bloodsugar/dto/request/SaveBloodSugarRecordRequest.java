package com.juju.cozyformombackend3.domain.bloodsugar.dto.request;

import com.juju.cozyformombackend3.domain.bloodsugar.model.BloodSugarRecordType;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SaveBloodSugarRecordRequest {

	private LocalDate date;
	private String type;
	private double level;
	
	public BloodSugarRecordType getType() {
		return BloodSugarRecordType.valueOf(type);
	}
}
