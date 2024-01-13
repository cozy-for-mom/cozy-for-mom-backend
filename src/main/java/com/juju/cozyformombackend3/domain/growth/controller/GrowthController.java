package com.juju.cozyformombackend3.domain.growth.controller;

import com.juju.cozyformombackend3.domain.growth.dto.request.SaveGrowthRequest;
import com.juju.cozyformombackend3.domain.growth.dto.response.SaveGrowthResponse;
import com.juju.cozyformombackend3.domain.growth.service.GrowthService;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/growth")
public class GrowthController {

	private final GrowthService growthService;

	@PostMapping
	public ResponseEntity<SuccessResponse> createGrowth(@RequestBody SaveGrowthRequest request) {
		User user = new User();

		SaveGrowthResponse response = growthService.saveGrowth(user, request);
		URI uri = URI.create("/api/v1/growth/" + response.getGrowthDiaryId());
		
		return ResponseEntity.created(uri).body(SuccessResponse.of(201, response));
	}
}
