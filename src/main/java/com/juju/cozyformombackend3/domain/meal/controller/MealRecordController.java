package com.juju.cozyformombackend3.domain.meal.controller;

import com.juju.cozyformombackend3.domain.meal.dto.request.CreateMealRecordRequest;
import com.juju.cozyformombackend3.domain.meal.dto.response.CreateMealRecordResponse;
import com.juju.cozyformombackend3.domain.meal.service.MealRecordService;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meal")
public class MealRecordController {

	private final MealRecordService mealRecordService;

	@PostMapping
	public ResponseEntity<SuccessResponse> saveMealRecord(@RequestBody CreateMealRecordRequest request) {
		User user = new User();

		CreateMealRecordResponse response = mealRecordService.creatdMealRecord(user, request);
		URI uri = URI.create("/api/v1/meal/" + response.getId());

		return ResponseEntity.created(uri).body(SuccessResponse.of(201, response));
	}
}
