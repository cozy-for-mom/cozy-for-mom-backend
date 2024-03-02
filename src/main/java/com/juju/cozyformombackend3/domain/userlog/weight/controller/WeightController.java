package com.juju.cozyformombackend3.domain.userlog.weight.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.userlog.weight.dto.request.RecordWeightRequest;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.request.UpdateWeightRequest;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.response.GetWeightListResponse;
import com.juju.cozyformombackend3.domain.userlog.weight.service.WeightService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/weight")
public class WeightController {

	private final WeightService weightService;

	@PostMapping()
	public ResponseEntity<SuccessResponse> recordWeight(
		@LoginUserId Long userId,
		@RequestBody RecordWeightRequest request) {
		weightService.recordWeight(userId, request);
		return ResponseEntity.ok(SuccessResponse.of(201, null));
	}

	@PutMapping()
	public ResponseEntity<SuccessResponse> updateWeight(
		@LoginUserId Long userId,
		@RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
		@RequestBody UpdateWeightRequest request) {
		weightService.updateWeight(userId, date, request);
		return ResponseEntity.ok(SuccessResponse.of(200, null));
	}

	@DeleteMapping()
	public ResponseEntity<SuccessResponse> deleteWeight(
		@LoginUserId Long userId,
		@RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		weightService.deleteWeight(userId, date);
		return ResponseEntity.ok(SuccessResponse.of(204, null));
	}

	@GetMapping()
	public ResponseEntity<SuccessResponse> getWeight(
		@LoginUserId Long userId,
		@RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
		@RequestParam(name = "type") String type) {
		GetWeightListResponse response = weightService.getWeight(userId, date, type);

		return ResponseEntity.ok(SuccessResponse.of(200, response));
	}
}
