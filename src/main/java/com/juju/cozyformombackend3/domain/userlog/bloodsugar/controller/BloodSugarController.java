package com.juju.cozyformombackend3.domain.userlog.bloodsugar.controller;

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

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.object.FindPeriodBloodSugarCondition;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.request.ModifyBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.request.SaveBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.FindBloodSugarListResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.FindDailyBloodSugarListResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.ModifyBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.SaveBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.service.BloodSugarService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.request.RecordPeriod;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/bloodsugar")
public class BloodSugarController {

	private final BloodSugarService bloodSugarService;

	@PostMapping
	public ResponseEntity<SuccessResponse> saveBloodSugarRecord(@RequestBody SaveBloodSugarRecordRequest request) {
		User user = new User();

		SaveBloodSugarRecordResponse response = bloodSugarService.saveBloodSugarRecord(request, user);
		URI uri = URI.create("/api/v1/bloodsugar/" + response.getBloodSugarRecordId());

		return ResponseEntity.created(uri).body(SuccessResponse.of(201, response));
	}

	@PutMapping
	public ResponseEntity<SuccessResponse> modifyBloodSugarRecord(@PathVariable(name = "id") Long id,
		@RequestBody ModifyBloodSugarRecordRequest request) {
		User user = new User();

		ModifyBloodSugarRecordResponse response = bloodSugarService.updateBloodSugarRecord(id, request, user);

		return ResponseEntity.ok().body(SuccessResponse.of(200, response));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<SuccessResponse> removeBloodSugarRecord(@PathVariable(name = "id") Long id) {
		User user = new User();

		bloodSugarService.deleteBloodSugarRecord(id, user);

		return ResponseEntity.ok().body(SuccessResponse.of(200, null));
	}

	@GetMapping()
	public ResponseEntity<SuccessResponse> searchDailyBloodSugarRecord(@RequestParam(name = "date") String date) {
		// TODO: 2021-10-04 date validation
		FindDailyBloodSugarListResponse response = bloodSugarService.findDailyBloodSugarRecord(1L, date);

		return ResponseEntity.ok().body(SuccessResponse.of(200, response));
	}

	@GetMapping("/period")
	public ResponseEntity<SuccessResponse> searchBloodSugarRecord(
		@LoginUserId Long userId,
		@RequestParam(name = "date") String date,
		@RequestParam(name = "type", defaultValue = "daily") RecordPeriod type,
		@RequestParam(name = "size", defaultValue = "10") Long size) {
		FindBloodSugarListResponse response = bloodSugarService.findBloodSugarRecord(
			FindPeriodBloodSugarCondition.of(userId, date, type, size));

		return ResponseEntity.ok().body(SuccessResponse.of(200, response));
	}
}
