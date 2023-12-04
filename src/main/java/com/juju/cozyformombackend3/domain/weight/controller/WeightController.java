package com.juju.cozyformombackend3.domain.weight.controller;

import com.juju.cozyformombackend3.domain.weight.dto.request.RecordWeightRequest;
import com.juju.cozyformombackend3.domain.weight.service.WeightService;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/weight")
public class WeightController {

	private final WeightService weightService;

	@PostMapping()
	public ResponseEntity<SuccessResponse> recordWeight(@RequestBody RecordWeightRequest request) {
		weightService.recordWeight(request);
		return ResponseEntity.ok(SuccessResponse.of(LocalDateTime.now(), 201, "ok", null));
	}

}
