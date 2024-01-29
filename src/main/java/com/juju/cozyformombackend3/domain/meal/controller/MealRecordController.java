package com.juju.cozyformombackend3.domain.meal.controller;

import com.juju.cozyformombackend3.domain.meal.dto.request.CreateMealRecordRequest;
import com.juju.cozyformombackend3.domain.meal.dto.request.UpdateMealRecordRequest;
import com.juju.cozyformombackend3.domain.meal.dto.response.CreateMealRecordResponse;
import com.juju.cozyformombackend3.domain.meal.dto.response.GetMealRecordResponse;
import com.juju.cozyformombackend3.domain.meal.dto.response.UpdateMealRecordResponse;
import com.juju.cozyformombackend3.domain.meal.service.MealRecordService;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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

    @PutMapping
    public ResponseEntity<SuccessResponse> modifyMealRecord(@RequestBody UpdateMealRecordRequest request) {
        User user = new User();

        UpdateMealRecordResponse response = mealRecordService.updateMealRecord(request, user);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> removeMealRecord(@PathVariable(name = "id") Long id) {
        User user = new User();

        mealRecordService.deleteMealRecord(id, user);

        return ResponseEntity.ok().body(SuccessResponse.of(200, null));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getMealRecord(@RequestParam(name = "date") String date) {
        Long userId = 1L;
        GetMealRecordResponse response = mealRecordService.getMealRecord(userId, date);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }
}
