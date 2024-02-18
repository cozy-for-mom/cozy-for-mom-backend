package com.juju.cozyformombackend3.domain.userlog.meal.dto.object;

import java.time.LocalDateTime;

import com.juju.cozyformombackend3.domain.userlog.meal.model.MealType;
import com.juju.cozyformombackend3.global.util.DateParser;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;

@Getter
public class DailyMealRecord {
	private final Long mealId;
	private final String dateTime;
	private final String mealType;
	private final String mealImageUrl;

	@QueryProjection
	public DailyMealRecord(Long mealId, LocalDateTime dateTime, MealType mealType, String mealImageUrl) {
		this.mealId = mealId;
		this.dateTime = DateParser.dateTimeToStringFormatDateTime(dateTime);
		this.mealType = mealType.getType();
		this.mealImageUrl = mealImageUrl;
	}
}
