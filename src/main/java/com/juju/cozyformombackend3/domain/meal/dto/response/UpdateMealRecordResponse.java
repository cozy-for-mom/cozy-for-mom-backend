package com.juju.cozyformombackend3.domain.meal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateMealRecordResponse {

	private Long id;

	public static UpdateMealRecordResponse of(Long id) {
		return new UpdateMealRecordResponse(id);
	}
}
