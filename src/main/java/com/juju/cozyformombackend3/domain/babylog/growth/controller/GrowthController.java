package com.juju.cozyformombackend3.domain.babylog.growth.controller;

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

import com.juju.cozyformombackend3.domain.babylog.growth.dto.request.SaveGrowthRequest;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.request.UpdateGrowthRequest;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.response.FindGrowthResponse;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.response.SaveGrowthResponse;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.response.UpdateGrowthResponse;
import com.juju.cozyformombackend3.domain.babylog.growth.service.GrowthService;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/growth")
public class GrowthController {

	private final GrowthService growthService;

	@PostMapping
	public ResponseEntity<SuccessResponse> createGrowth(
		@LoginUserId Long userId,
		@RequestBody SaveGrowthRequest request) {

		SaveGrowthResponse response = growthService.saveGrowth(userId, request);
		URI uri = URI.create("/api/v1/growth/" + response.getGrowthDiaryId());

		return ResponseEntity.created(uri).body(SuccessResponse.of(201, response));
	}

	@PutMapping("/{id}")
	public ResponseEntity<SuccessResponse> modifyGrowth(
		@LoginUserId Long userId,
		@PathVariable(name = "id") Long reportId,
		@RequestBody UpdateGrowthRequest request) {
		UpdateGrowthResponse response = growthService.updateGrowth(userId, reportId, request);

		return ResponseEntity.ok(SuccessResponse.of(200, response));
	}

	@DeleteMapping
	public ResponseEntity<SuccessResponse> removeGrowth(
		@RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		User user = new User();

		growthService.deleteGrowth(user, date);

		return ResponseEntity.ok(SuccessResponse.of(200, null));
	}

	@GetMapping("/{id}")
	public ResponseEntity<SuccessResponse> getGrowth(
		@LoginUserId Long userId,
		@PathVariable(name = "id") Long reportId) {
		FindGrowthResponse response = growthService.getGrowth(userId, reportId);

		return ResponseEntity.ok().body(SuccessResponse.of(200, response));
	}
}
