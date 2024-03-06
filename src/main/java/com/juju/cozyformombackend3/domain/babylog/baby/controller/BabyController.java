package com.juju.cozyformombackend3.domain.babylog.baby.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.babylog.baby.dto.request.CreateBabyProfileRequest;
import com.juju.cozyformombackend3.domain.babylog.baby.dto.response.CreateBabyProfileResponse;
import com.juju.cozyformombackend3.domain.babylog.baby.service.BabyService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/baby")
public class BabyController {
	private final BabyService babyService;

	@PostMapping
	public ResponseEntity<SuccessResponse> createBabyProfile(
		@LoginUserId Long userId,
		@RequestBody CreateBabyProfileRequest request) {
		CreateBabyProfileResponse response = babyService.saveBabyProfile(userId, request);

		return ResponseEntity.ok().body(SuccessResponse.of(201, response));
	}
}
