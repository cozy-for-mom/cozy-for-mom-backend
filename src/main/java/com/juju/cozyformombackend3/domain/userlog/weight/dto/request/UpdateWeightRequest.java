package com.juju.cozyformombackend3.domain.userlog.weight.dto.request;

import java.time.LocalDate;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.userlog.weight.model.WeightRecord;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UpdateWeightRequest {

	private double weight;

	public WeightRecord toEntity(LocalDate date, User user) {
		return WeightRecord.builder()
			.recordDate(date)
			.user(user)
			.weight(weight)
			.build();
	}
}