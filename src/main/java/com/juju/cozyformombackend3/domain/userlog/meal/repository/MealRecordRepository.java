package com.juju.cozyformombackend3.domain.userlog.meal.repository;

import com.juju.cozyformombackend3.domain.userlog.meal.model.MealRecord;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRecordRepository extends JpaRepository<MealRecord, Long>, CustomMealRecordRepository {

}
