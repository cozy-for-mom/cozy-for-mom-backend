package com.juju.cozyformombackend3.domain.communitylog.cozylog.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.CreateCozyLogRequest;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response.CreateCozyLogResponse;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.service.CozyLogService;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cozy-log")
@RequiredArgsConstructor
public class CozyLogController {
	private final CozyLogService cozyLogService;

	@PostMapping
	public ResponseEntity<SuccessResponse> createCozyLog(@RequestBody CreateCozyLogRequest request) {
		User user = new User();

		Long createdCozyLogId = cozyLogService.createCozyLog(user, request);
		URI location = URI.create("/api/v1/cozy-log/" + createdCozyLogId);

		return ResponseEntity.created(location)
			.body(SuccessResponse.of(201, CreateCozyLogResponse.of(createdCozyLogId)));
	}
}
