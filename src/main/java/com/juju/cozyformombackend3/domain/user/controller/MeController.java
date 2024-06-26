package com.juju.cozyformombackend3.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.user.dto.request.UpdateMyInfoRequest;
import com.juju.cozyformombackend3.domain.user.dto.request.UpdateRecentBabyProfileRequest;
import com.juju.cozyformombackend3.domain.user.dto.response.FindMyInfoResponse;
import com.juju.cozyformombackend3.domain.user.dto.response.UpdateMyInfoResponse;
import com.juju.cozyformombackend3.domain.user.dto.response.UpdateRecentBabyProfileResponse;
import com.juju.cozyformombackend3.domain.user.service.MeService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/me")
@RequiredArgsConstructor
public class MeController {
    private final MeService meService;

    @GetMapping
    public ResponseEntity<SuccessResponse> getMe(
        @Parameter(hidden = true) @LoginUserId Long userId) {
        FindMyInfoResponse response = meService.findMe(userId);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @PutMapping
    public ResponseEntity<SuccessResponse> modifyMe(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(
                schema = @Schema(implementation = UpdateMyInfoRequest.class)))
        @RequestBody @Valid UpdateMyInfoRequest request) {
        UpdateMyInfoResponse response = meService.updateMe(userId, request);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @PostMapping("/baby/recent")
    public ResponseEntity<SuccessResponse> modifyRecentBabyProfile(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(
                schema = @Schema(implementation = UpdateRecentBabyProfileRequest.class)))
        @RequestBody @Valid UpdateRecentBabyProfileRequest request) {
        UpdateRecentBabyProfileResponse response = meService.updateRecentBabyProfile(userId, request);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }
}
