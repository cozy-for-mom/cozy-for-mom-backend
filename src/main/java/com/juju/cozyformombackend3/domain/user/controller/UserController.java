package com.juju.cozyformombackend3.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.user.dto.request.UpdateMyInfoRequest;
import com.juju.cozyformombackend3.domain.user.dto.request.UpdateRecentBabyProfileRequest;
import com.juju.cozyformombackend3.domain.user.dto.response.FindMyInfoResponse;
import com.juju.cozyformombackend3.domain.user.dto.response.UpdateMyInfoResponse;
import com.juju.cozyformombackend3.domain.user.dto.response.UpdateRecentBabyProfileResponse;
import com.juju.cozyformombackend3.domain.user.service.UserService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	@GetMapping
	public ResponseEntity<SuccessResponse> getMe(
		@LoginUserId Long userId) {
		FindMyInfoResponse response = userService.findMe(userId);

		return ResponseEntity.ok().body(SuccessResponse.of(200, response));
	}

	@PutMapping
	public ResponseEntity<SuccessResponse> modifyMe(
		@LoginUserId Long userId,
		@RequestBody UpdateMyInfoRequest request) {
		UpdateMyInfoResponse response = userService.updateMe(userId, request);

		return ResponseEntity.ok().body(SuccessResponse.of(200, response));
	}

	@PostMapping("/baby/recent")
	public ResponseEntity<SuccessResponse> modifyRecentBabyProfile(
		@LoginUserId Long userId,
		@RequestBody UpdateRecentBabyProfileRequest request) {
		UpdateRecentBabyProfileResponse response = userService.updateRecentBabyProfile(userId, request);

		return ResponseEntity.ok().body(SuccessResponse.of(200, response));
	}
}
