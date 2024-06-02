package com.juju.cozyformombackend3.domain.babylog.baby.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.babylog.baby.controller.dto.CreateBabyProfile;
import com.juju.cozyformombackend3.domain.babylog.baby.controller.dto.ModifyBabyProfile;
import com.juju.cozyformombackend3.domain.babylog.baby.controller.dto.RemoveBabyProfile;
import com.juju.cozyformombackend3.domain.babylog.baby.service.BabyService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/baby")
public class BabyController {
    private final BabyService babyService;

    @PostMapping
    public ResponseEntity<SuccessResponse> createBabyProfile(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(
                schema = @Schema(implementation = CreateBabyProfile.Request.class)))
        @RequestBody @Valid CreateBabyProfile.Request request) {
        CreateBabyProfile.Response response = babyService.saveBabyProfile(userId, request);
        final URI uri = URI.create("/api/v1/baby/" + response.getBabyProfileId());

        return ResponseEntity.created(uri).body(SuccessResponse.of(201, response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> modifyBabyProfile(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long babyProfileId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(
                schema = @Schema(implementation = ModifyBabyProfile.Request.class)))
        @RequestBody @Valid ModifyBabyProfile.Request request) {
        ModifyBabyProfile.Response response = babyService.updateBabyProfile(userId, babyProfileId, request);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> removeBabyProfile(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long babyProfileId) {
        RemoveBabyProfile.Response response = babyService.deleteBabyProfile(userId, babyProfileId);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }
}
