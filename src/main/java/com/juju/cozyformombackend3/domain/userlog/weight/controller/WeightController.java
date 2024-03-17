package com.juju.cozyformombackend3.domain.userlog.weight.controller;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.userlog.weight.dto.request.RecordWeightRequest;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.request.UpdateWeightRequest;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.response.FindWeightListResponse;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.response.RecordWeightResponse;
import com.juju.cozyformombackend3.domain.userlog.weight.dto.response.UpdateWeightResponse;
import com.juju.cozyformombackend3.domain.userlog.weight.service.WeightService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.request.FindPeriodRecordCondition;
import com.juju.cozyformombackend3.global.dto.request.RecordPeriod;
import com.juju.cozyformombackend3.global.dto.response.ErrorResponse;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "몸무게 조회")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/weight")
public class WeightController {

    private final WeightService weightService;

    @Operation(description = "몸무게 등록",
        responses = {
            @ApiResponse(responseCode = "201", description = "몸무게 등록 성공", content = @Content(schema = @Schema(implementation = RecordWeightResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 날짜에 몸무게 재등록", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))}
    )
    @PostMapping()
    public ResponseEntity<SuccessResponse> recordWeight(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @RequestBody @Valid RecordWeightRequest request) {
        RecordWeightResponse response = weightService.recordWeight(userId, request);

        return ResponseEntity.ok(SuccessResponse.of(201, response));
    }

    @Operation(description = "몸무게 수정",
        responses = {
            @ApiResponse(responseCode = "201", description = "몸무게 등록 성공", content = @Content(schema = @Schema(implementation = RecordWeightResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 날짜에 몸무게 재등록", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping()
    public ResponseEntity<SuccessResponse> updateWeight(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @Parameter(name = "date", in = ParameterIn.PATH, description = "조회 날짜", example = "2024-03-17")
        @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestBody @Valid UpdateWeightRequest request) {
        UpdateWeightResponse response = weightService.updateWeight(userId, date, request);

        return ResponseEntity.ok(SuccessResponse.of(200, response));
    }

    @Operation(description = "몸무게 삭제",
        responses = {
            @ApiResponse(responseCode = "201", description = "몸무게 등록 성공", content = @Content(schema = @Schema(implementation = RecordWeightResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 날짜에 몸무게 재등록", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping()
    public ResponseEntity<SuccessResponse> deleteWeight(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        weightService.deleteWeight(userId, date);

        return ResponseEntity.ok(SuccessResponse.of(204, null));
    }

    @Operation(description = "몸무게 기간별 조회",
        responses = {
            @ApiResponse(responseCode = "201", description = "몸무게 등록 성공", content = @Content(schema = @Schema(implementation = RecordWeightResponse.class))),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 날짜에 몸무게 재등록", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping()
    public ResponseEntity<SuccessResponse> getWeight(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam(name = "type", defaultValue = "daily") RecordPeriod type,
        @RequestParam(name = "size", defaultValue = "10") Long size) {
        FindWeightListResponse response = weightService.findWeight(
            FindPeriodRecordCondition.of(userId, date, type, size));

        return ResponseEntity.ok(SuccessResponse.of(200, response));
    }
}
