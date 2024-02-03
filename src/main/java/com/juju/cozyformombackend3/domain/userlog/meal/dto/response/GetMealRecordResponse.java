package com.juju.cozyformombackend3.domain.userlog.meal.dto.response;

import com.juju.cozyformombackend3.domain.userlog.meal.dto.object.DailyMealRecord;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetMealRecordResponse {
	List<DailyMealRecord> mealRecordList;

	public static GetMealRecordResponse of(List<DailyMealRecord> mealRecordList) {
		return new GetMealRecordResponse(mealRecordList);
	}
}
