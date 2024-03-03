package com.juju.cozyformombackend3.domain.communitylog.cozylog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.DeleteMyCozyLogListRequest;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response.FindMyCozyLogListResponse;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.service.CozyLogService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/me/cozy-log")
@RequiredArgsConstructor
public class MyCozyLogController {
	private final CozyLogService cozyLogService;

	@GetMapping
	public ResponseEntity<SuccessResponse> getMyCozyLogList(
		@LoginUserId Long userId,
		@RequestParam(value = "lastId") Long reportId,
		@RequestParam(value = "size", defaultValue = "10") Long size) {
		FindMyCozyLogListResponse response = cozyLogService.findMyCozyLog(userId, reportId, size);

		return ResponseEntity.ok().body(SuccessResponse.of(200, response));
	}

	@PostMapping
	public ResponseEntity<SuccessResponse> removeMyCozyLogList(
		@LoginUserId Long userId,
		@RequestBody DeleteMyCozyLogListRequest request) {
		cozyLogService.deleteCozyLogList(userId, request);

		return ResponseEntity.ok().body(SuccessResponse.of(200, null));
	}
}
