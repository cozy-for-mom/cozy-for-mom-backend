package com.juju.cozyformombackend3.domain.userlog.meal.repository;

import com.juju.cozyformombackend3.domain.userlog.meal.dto.object.DailyMealRecord;

import java.util.List;

public interface CustomMealRecordRepository {
	List<DailyMealRecord> searchAllByUserIdAndDate(Long userId, String date);
}
