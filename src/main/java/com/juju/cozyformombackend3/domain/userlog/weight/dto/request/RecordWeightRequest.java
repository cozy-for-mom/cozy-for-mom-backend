package com.juju.cozyformombackend3.domain.userlog.weight.dto.request;

import java.time.LocalDate;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.userlog.weight.model.WeightRecord;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RecordWeightRequest {

	private LocalDate date;
	private double weight;

	public WeightRecord toEntity(User user) {
		return WeightRecord.builder()
			.recordAt(date)
			.user(user)
			.weight(weight)
			.build();
	}
}
