package com.juju.cozyformombackend3.domain.bloodsugar.controller;

import com.juju.cozyformombackend3.domain.bloodsugar.dto.request.ModifyBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.request.SaveBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.response.ModifyBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.response.SaveBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.bloodsugar.service.BloodSugarService;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		URI uri = URI.create("/api/v1/bloodsugar/" + response.getBloodSugarRecordId());

		return ResponseEntity.created(uri).body(SuccessResponse.of(201, response));
	}

	@PutMapping
	public ResponseEntity<SuccessResponse> modifyBloodSugarRecord(@RequestBody ModifyBloodSugarRecordRequest request) {
		User user = new User();

		ModifyBloodSugarRecordResponse response = bloodSugarService.updateBloodSugarRecord(request, user);

		return ResponseEntity.ok().body(SuccessResponse.of(200, response));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<SuccessResponse> removeBloodSugarRecord(@PathVariable(name = "id") Long id) {
		User user = new User();

		bloodSugarService.deleteBloodSugarRecord(id, user);

		return ResponseEntity.ok().body(SuccessResponse.of(200, null));
	}
}
