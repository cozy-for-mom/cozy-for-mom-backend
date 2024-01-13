package com.juju.cozyformombackend3.domain.meal.dto.request;

import com.juju.cozyformombackend3.domain.meal.model.MealType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateMealRecordRequest {

	private LocalDateTime datetime;
	private String mealType;
	private String mealImageUrl;

	public MealType getMealType() {
		return MealType.ofType(this.mealType);
	}
}
