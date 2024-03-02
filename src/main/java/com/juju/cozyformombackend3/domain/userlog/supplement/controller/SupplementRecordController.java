package com.juju.cozyformombackend3.domain.userlog.supplement.controller;

import java.net.URI;

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

import com.juju.cozyformombackend3.domain.userlog.supplement.dto.request.DeleteSupplementRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.request.SaveSupplementRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.request.UpdateSupplementRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.response.GetDailySupplementResponse;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.response.SaveSupplementRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.response.UpdateSupplementRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.supplement.service.SupplementRecordService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.request.ListRequest;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/supplement/intake")
public class SupplementRecordController {

	private final SupplementRecordService supplementRecordService;

	@PostMapping("")
	public ResponseEntity<SuccessResponse> createSupplementRecord(
		@LoginUserId Long userId,
		@RequestBody SaveSupplementRecordRequest request) {
		SaveSupplementRecordResponse response = supplementRecordService.saveSupplementRecord(userId, request);
		URI uri = URI.create("/api/v1/supplement/intake/" + response.getSupplementRecordId());

		return ResponseEntity.created(uri).body(SuccessResponse.of(201, response));
	}

	@PutMapping("/{id}")
	public ResponseEntity<SuccessResponse> modifySupplementRecord(
		@LoginUserId Long userId,
		@PathVariable(name = "id") Long recordId,
		@RequestBody UpdateSupplementRecordRequest request) {
		UpdateSupplementRecordResponse response = supplementRecordService
			.updateSupplementRecord(userId, recordId, request);

		return ResponseEntity.ok().body(SuccessResponse.of(200, response));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<SuccessResponse> deleteSupplementRecord(
		@LoginUserId Long userId,
		@PathVariable(name = "id") Long recordId,
		@RequestBody ListRequest<DeleteSupplementRecordRequest> request) {
		supplementRecordService.deleteSupplementRecord(userId, request);

		return ResponseEntity.ok().body(SuccessResponse.of(200, null));
	}

	@GetMapping
	public ResponseEntity<SuccessResponse> getSupplementRecord(
		@LoginUserId Long userId,
		@RequestParam(name = "date") String date) {
		GetDailySupplementResponse response = supplementRecordService.getSupplementRecord(userId, date);

		return ResponseEntity.ok().body(SuccessResponse.of(200, response));
	}
}
