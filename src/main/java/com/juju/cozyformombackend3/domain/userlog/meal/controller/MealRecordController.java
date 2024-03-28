package com.juju.cozyformombackend3.domain.userlog.meal.controller;

import java.net.URI;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.userlog.meal.dto.request.CreateMealRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.meal.dto.request.UpdateMealRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.meal.dto.response.CreateMealRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.meal.dto.response.GetMealRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.meal.dto.response.UpdateMealRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.meal.service.MealRecordService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/meal")
public class MealRecordController {

    private final MealRecordService mealRecordService;

    @PostMapping
    public ResponseEntity<SuccessResponse> saveMealRecord(
        @LoginUserId Long userId,
        @RequestBody @Valid CreateMealRecordRequest request) {
        CreateMealRecordResponse response = mealRecordService.createdMealRecord(userId, request);
        URI uri = URI.create("/api/v1/meal/" + response.getId());

        return ResponseEntity.created(uri).body(SuccessResponse.of(201, response));
    }

    @PutMapping
    public ResponseEntity<SuccessResponse> modifyMealRecord(
        @LoginUserId Long userId,
        @RequestBody @Valid UpdateMealRecordRequest request) {
        UpdateMealRecordResponse response = mealRecordService.updateMealRecord(userId, request);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> removeMealRecord(
        @LoginUserId Long userId,
        @PathVariable(name = "id") Long id) {
        mealRecordService.deleteMealRecord(userId, id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getMealRecord(
        @LoginUserId Long userId,
        @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        GetMealRecordResponse response = mealRecordService.getMealRecord(userId, String.valueOf(date));

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }
}
