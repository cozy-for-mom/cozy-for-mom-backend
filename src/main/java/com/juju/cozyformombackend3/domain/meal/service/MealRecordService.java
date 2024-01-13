package com.juju.cozyformombackend3.domain.meal.service;

import com.juju.cozyformombackend3.domain.meal.dto.request.CreateMealRecordRequest;
import com.juju.cozyformombackend3.domain.meal.dto.response.CreateMealRecordResponse;
import com.juju.cozyformombackend3.domain.meal.repository.MealRecordRepository;
import com.juju.cozyformombackend3.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MealRecordService {

	private final MealRecordRepository mealRecordRepository;

	@Transactional
	public CreateMealRecordResponse creatdMealRecord(User user, CreateMealRecordRequest request) {
		Long savedRecordId = user.addMealRecord(request.getDatetime(), request.getMealType(), request.getMealImageUrl());
		
		return CreateMealRecordResponse.of(savedRecordId);
	}
}
