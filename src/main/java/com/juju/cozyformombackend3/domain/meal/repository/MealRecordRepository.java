package com.juju.cozyformombackend3.domain.meal.repository;

import com.juju.cozyformombackend3.domain.meal.model.MealRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRecordRepository extends JpaRepository<MealRecord, Long> {

}
