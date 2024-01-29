package com.juju.cozyformombackend3.domain.meal.dto.object;

import com.juju.cozyformombackend3.domain.meal.model.MealType;
import com.juju.cozyformombackend3.global.util.DateParser;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DailyMealRecord {
    private final Long mealId;
    private final String dateTime;
    private final String mealType;
    private final String mealImageUrl;

    @QueryProjection
    public DailyMealRecord(Long mealId, LocalDateTime dateTime, MealType mealType, String mealImageUrl) {
        this.mealId = mealId;
        this.dateTime = DateParser.dateTimeToString(dateTime);
        this.mealType = mealType.getType();
        this.mealImageUrl = mealImageUrl;
    }
}
