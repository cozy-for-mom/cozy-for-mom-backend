package com.juju.cozyformombackend3.domain.weight.dto.request;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.weight.model.WeightRecord;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RecordWeightRequest {

	private LocalDate date;
	private double weight;

	public WeightRecord toEntity(User user) {
		return WeightRecord.builder()
						.recordDate(date)
						.user(user)
						.weight(weight)
						.build();
	}
}
