package com.juju.cozyformombackend3.domain.communitylog.scrap.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.communitylog.scrap.dto.request.ApplyScrapRequest;
import com.juju.cozyformombackend3.domain.communitylog.scrap.service.ScrapService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/scrap")
@RequiredArgsConstructor
public class ScrapController {
	private final ScrapService scrapService;

	@PostMapping()
	public ResponseEntity<SuccessResponse> scrapSave(@LoginUserId Long userId, @RequestBody ApplyScrapRequest request) {
		scrapService.saveScrap(userId, request);
		URI location = URI.create("/api/v1/cozy-log/" + request.getCozyLogId());

		return ResponseEntity.created(location).body(SuccessResponse.of(201, null));
	}

}
