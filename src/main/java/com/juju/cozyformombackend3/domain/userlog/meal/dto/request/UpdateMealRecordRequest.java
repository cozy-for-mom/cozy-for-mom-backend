package com.juju.cozyformombackend3.domain.userlog.meal.dto.request;

import com.juju.cozyformombackend3.domain.userlog.meal.model.MealType;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMealRecordRequest {

	private Long id;
	private LocalDateTime datetime;
	private String type;
	private String mealImageUrl;

	public MealType getMealType() {
		return MealType.ofType(this.type);
	}
}
