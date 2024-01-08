package com.juju.cozyformombackend3.domain.supplement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/supplement/intake")
public class SupplementRecordController {

	private final SupplementRecordService supplementRecordService;

	@PostMapping("/{userId}")
	public void registerSupplement(@RequestBody @Valid RegisterSupplementRequest request,
					@PathVariable(name = "userId") Long userId) {
		supplementService.registerSupplement(request, userId);
	}

	@PostMapping("/api/v1/supplement/intake/{userId}")
	public void postSupplementRecord(@RequestBody @Valid PostSupplementRecordRequest request,
					@PathVariable(name = "userId") Long userId) {
		supplementRecordService.postSupplementRecord(request, userId);
	}

	@DeleteMapping("/api/v1/supplement/intake/{userId}")
	public void deleteSupplementRecord(@RequestBody @Valid PostSupplementRecordRequest request,
					@PathVariable(name = "userId") Long userId) {
		// supplementRecordService.deleteSupplementRecord(request, userId);
	}
}
