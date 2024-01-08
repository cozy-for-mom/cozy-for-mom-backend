package com.juju.cozyformombackend3.domain.supplement.controller;

import com.juju.cozyformombackend3.domain.supplement.dto.request.RegisterSupplementRequest;
import com.juju.cozyformombackend3.domain.supplement.dto.response.RegisterSupplementResponse;
import com.juju.cozyformombackend3.domain.supplement.service.SupplementService;
import com.juju.cozyformombackend3.global.dto.response.CommonResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/supplement")
public class SupplementController {

	private final SupplementService supplementService;

	@PostMapping("")
	public ResponseEntity<CommonResponse> registerSupplement(@RequestBody RegisterSupplementRequest request) {
		Long userId = 1L;

		URI uri = URI.create("/api/v1/supplement/" + userId);
		RegisterSupplementResponse registerSupplementResponse = supplementService.registerSupplement(request, userId);
		return ResponseEntity.created(uri).body(CommonResponse.of(registerSupplementResponse));
	}

}
