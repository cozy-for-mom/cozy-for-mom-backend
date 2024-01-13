package com.juju.cozyformombackend3.domain.meal.service;

import com.juju.cozyformombackend3.domain.meal.dto.request.CreateMealRecordRequest;
import com.juju.cozyformombackend3.domain.meal.dto.request.UpdateMealRecordRequest;
import com.juju.cozyformombackend3.domain.meal.dto.response.CreateMealRecordResponse;
import com.juju.cozyformombackend3.domain.meal.dto.response.UpdateMealRecordResponse;
import com.juju.cozyformombackend3.domain.meal.model.MealRecord;
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

	@Transactional
	public UpdateMealRecordResponse updateMealRecord(UpdateMealRecordRequest request, User user) {
		MealRecord foundMealRecord = findMealRecordById(request.getId());
		isMealRecordOwner(foundMealRecord.getUser(), user);
		Long updatedRecordId = foundMealRecord.update(request.getDatetime(), request.getMealType(),
						request.getMealImageUrl());

		return UpdateMealRecordResponse.of(updatedRecordId);
	}

	@Transactional
	public void deleteMealRecord(Long id, User user) {
		MealRecord foundMealRecord = findMealRecordById(id);
		isMealRecordOwner(foundMealRecord.getUser(), user);

		mealRecordRepository.delete(foundMealRecord);
	}

	private MealRecord findMealRecordById(Long id) {
		return mealRecordRepository.findById(id)
						.orElseThrow(() -> new IllegalArgumentException("해당 식사 기록이 존재하지 않습니다."));
	}

	private void isMealRecordOwner(User recordOwner, User requestUser) {
		if (recordOwner != requestUser) {
			throw new IllegalArgumentException("해당 식사 기록에 대한 권한이 없습니다.");
		}
	}
}
