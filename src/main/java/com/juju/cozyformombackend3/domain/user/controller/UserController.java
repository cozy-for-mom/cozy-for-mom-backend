package com.juju.cozyformombackend3.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.user.dto.response.FindMyInfoResponse;
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
}
