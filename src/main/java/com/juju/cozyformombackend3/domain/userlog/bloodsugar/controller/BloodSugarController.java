package com.juju.cozyformombackend3.domain.userlog.bloodsugar.controller;

import java.net.URI;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.request.ModifyBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.request.SaveBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.FindBloodSugarListResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.FindDailyBloodSugarListResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.ModifyBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.dto.response.SaveBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.service.BloodSugarService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.request.FindPeriodRecordCondition;
import com.juju.cozyformombackend3.global.dto.request.RecordPeriod;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/bloodsugar")
public class BloodSugarController {

    private final BloodSugarService bloodSugarService;

    @PostMapping
    public ResponseEntity<SuccessResponse> saveBloodSugarRecord(
        @LoginUserId Long userId,
        @RequestBody @Valid SaveBloodSugarRecordRequest request) {

        SaveBloodSugarRecordResponse response = bloodSugarService.saveBloodSugarRecord(request, userId);
        URI uri = URI.create("/api/v1/bloodsugar/" + response.getBloodSugarRecordId());

        return ResponseEntity.created(uri).body(SuccessResponse.of(201, response));
    }

    @PutMapping
    public ResponseEntity<SuccessResponse> modifyBloodSugarRecord(
        @LoginUserId Long userId,
        @PathVariable(name = "id") Long id,
        @RequestBody @Valid ModifyBloodSugarRecordRequest request) {

        ModifyBloodSugarRecordResponse response = bloodSugarService.updateBloodSugarRecord(id, request, userId);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> removeBloodSugarRecord(
        @LoginUserId Long userId,
        @PathVariable(name = "id") Long id) {
        bloodSugarService.deleteBloodSugarRecord(id, userId);

        return ResponseEntity.ok().body(SuccessResponse.of(200, null));
    }

    @GetMapping()
    public ResponseEntity<SuccessResponse> searchDailyBloodSugarRecord(
        @LoginUserId Long userId,
        @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        // TODO: 2021-10-04 date validation
        FindDailyBloodSugarListResponse response = bloodSugarService.findDailyBloodSugarRecord(userId, date);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @GetMapping("/period")
    public ResponseEntity<SuccessResponse> searchBloodSugarRecord(
        @LoginUserId Long userId,
        @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam(name = "type", defaultValue = "daily") RecordPeriod type,
        @RequestParam(name = "size", defaultValue = "10") Long size) {
        FindBloodSugarListResponse response = bloodSugarService.findBloodSugarRecord(
            FindPeriodRecordCondition.of(userId, date, type, size));

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }
}
