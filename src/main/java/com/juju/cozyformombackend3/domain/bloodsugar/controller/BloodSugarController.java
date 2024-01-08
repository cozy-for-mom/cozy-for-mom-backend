package com.juju.cozyformombackend3.domain.bloodsugar.controller;

import com.juju.cozyformombackend3.domain.bloodsugar.dto.request.SaveBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.response.SaveBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.bloodsugar.service.BloodSugarService;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/bloodsugar")
public class BloodSugarController {

	private final BloodSugarService bloodSugarService;

	@PostMapping
	public ResponseEntity<SuccessResponse> saveBloodSugarRecord(@RequestBody SaveBloodSugarRecordRequest request) {
		User user = new User();

		SaveBloodSugarRecordResponse response = bloodSugarService.saveBloodSugarRecord(request, user);
		return ResponseEntity.ok().body(SuccessResponse.of(201, response));
	}
}
