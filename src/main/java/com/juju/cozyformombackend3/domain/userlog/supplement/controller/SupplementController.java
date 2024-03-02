package com.juju.cozyformombackend3.domain.userlog.supplement.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.userlog.supplement.dto.request.RegisterSupplementRequest;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.response.RegisterSupplementResponse;
import com.juju.cozyformombackend3.domain.userlog.supplement.service.SupplementService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.CommonResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/supplement")
public class SupplementController {

	private final SupplementService supplementService;

	@PostMapping("")
	public ResponseEntity<CommonResponse> registerSupplement(
		@LoginUserId Long userId,
		@RequestBody RegisterSupplementRequest request) {
		RegisterSupplementResponse registerSupplementResponse = supplementService.registerSupplement(userId, request);
		//TODO: 유의미한 userID로 변경
		URI uri = URI.create("/api/v1/supplement/" + userId);

		return ResponseEntity.created(uri).body(CommonResponse.of(registerSupplementResponse));
	}
}
