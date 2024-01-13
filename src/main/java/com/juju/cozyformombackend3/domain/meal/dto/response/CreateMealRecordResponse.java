package com.juju.cozyformombackend3.domain.meal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateMealRecordResponse {

	private Long id;

	public static CreateMealRecordResponse of(Long id) {
		return new CreateMealRecordResponse(id);
	}
}
