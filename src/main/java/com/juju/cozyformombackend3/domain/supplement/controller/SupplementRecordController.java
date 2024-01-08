package com.juju.cozyformombackend3.domain.supplement.controller;

import com.juju.cozyformombackend3.domain.supplement.dto.request.SaveSupplementRecordRequest;
import com.juju.cozyformombackend3.domain.supplement.dto.response.SaveSupplementRecordResponse;
import com.juju.cozyformombackend3.domain.supplement.service.SupplementRecordService;
import com.juju.cozyformombackend3.domain.user.model.User;
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
@RequestMapping("api/v1/supplement/intake")
public class SupplementRecordController {

	private final SupplementRecordService supplementRecordService;

	@PostMapping("")
	public ResponseEntity<CommonResponse> createSupplementRecord(@RequestBody SaveSupplementRecordRequest request) {
		Long userId = 1L;
		User user = new User();

		SaveSupplementRecordResponse response = supplementRecordService.saveSupplementRecord(request, user);
		URI uri = URI.create("/api/v1/supplement/intake/" + response.getSupplementRecordId());

		return ResponseEntity.created(uri).body(CommonResponse.of(response));
	}

	//	@GetMapping()
	//	public
	//
	//	@DeleteMapping("/api/v1/supplement/intake/{userId}")
	//	public void deleteSupplementRecord(@RequestBody PostSupplementRecordRequest request) {
	//		// supplementRecordService.deleteSupplementRecord(request, userId);
	//	}
}
