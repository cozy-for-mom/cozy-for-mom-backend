package com.juju.cozyformombackend3.domain.meal.repository;

import com.juju.cozyformombackend3.domain.meal.dto.object.DailyMealRecord;

import java.util.List;

public interface CustomMealRecordRepository {
    List<DailyMealRecord> searchAllByUserIdAndDate(Long userId, String date);
}
