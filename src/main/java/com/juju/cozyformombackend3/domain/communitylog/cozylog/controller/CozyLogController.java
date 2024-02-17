package com.juju.cozyformombackend3.domain.communitylog.cozylog.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.CreateCozyLogRequest;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.ModifyCozyLogRequest;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response.CozyLogDetailResponse;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response.CreateCozyLogResponse;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response.ModifyCozyLogResponse;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.service.CozyLogService;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
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

		Long createdCozyLogId = cozyLogService.saveCozyLog(user, request);
		URI location = URI.create("/api/v1/cozy-log/" + createdCozyLogId);

		return ResponseEntity.created(location)
			.body(SuccessResponse.of(201, CreateCozyLogResponse.of(createdCozyLogId)));
	}

	@PutMapping
	public ResponseEntity<SuccessResponse> modifyCozyLog(@RequestBody ModifyCozyLogRequest request) {
		User user = new User();
		Long modifiedCozyLogId = cozyLogService.updateCozyLog(user, request);

		return ResponseEntity.ok(SuccessResponse.of(200, ModifyCozyLogResponse.of(modifiedCozyLogId)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removeCozyLog(@PathVariable("id") Long removeCozyLogId) {
		Long userId = 1L;

		userId = cozyLogService.deleteCozyLog(userId, removeCozyLogId);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<SuccessResponse> cozyLogDetail(@LoginUserId Long userId, @PathVariable("id") Long cozyLogId) {
		CozyLogDetailResponse response = cozyLogService.findCozyLogDetail(userId, cozyLogId);

		return ResponseEntity.ok(SuccessResponse.of(200, response));
	}
}
