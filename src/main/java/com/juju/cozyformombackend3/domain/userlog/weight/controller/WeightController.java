package com.juju.cozyformombackend3.domain.userlog.weight.controller;

import com.juju.cozyformombackend3.domain.userlog.weight.dto.request.DeleteWeightRequest;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.request.RecordWeightRequest;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.response.GetWeightListResponse;
import com.juju.cozyformombackend3.domain.userlog.weight.service.WeightService;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/weight")
public class WeightController {

	private final WeightService weightService;

	@PostMapping()
	public ResponseEntity<SuccessResponse> recordWeight(@RequestBody RecordWeightRequest request) {
		weightService.recordWeight(request);
		return ResponseEntity.ok(SuccessResponse.of(201, null));
	}

	@PutMapping()
	public ResponseEntity<SuccessResponse> updateWeight(@RequestBody RecordWeightRequest request) {
		weightService.updateWeight(request);
		return ResponseEntity.ok(SuccessResponse.of(200, null));
	}

	@DeleteMapping()
	public ResponseEntity<SuccessResponse> deleteWeight(@RequestBody DeleteWeightRequest request) {
		weightService.deleteWeight(request);
		return ResponseEntity.ok(SuccessResponse.of(204, null));
	}

	@GetMapping()
	public ResponseEntity<SuccessResponse> getWeight(
		@RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
		@RequestParam(name = "type") String type) {
		GetWeightListResponse response = weightService.getWeight(1L, date, type);

		return ResponseEntity.ok(SuccessResponse.of(200, response));
	}
}
