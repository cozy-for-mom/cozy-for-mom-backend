package com.juju.cozyformombackend3.domain.userlog.supplement.controller;

import java.net.URI;
import java.time.LocalDate;

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

import com.juju.cozyformombackend3.domain.userlog.supplement.dto.request.SaveSupplementRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.request.UpdateSupplementRecordRequest;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.response.GetDailySupplementResponse;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.response.SaveSupplementRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.response.UpdateSupplementRecordResponse;
import com.juju.cozyformombackend3.domain.userlog.supplement.service.SupplementRecordService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/supplement/intake")
public class SupplementRecordController {

    private final SupplementRecordService supplementRecordService;

    @PostMapping("")
    public ResponseEntity<SuccessResponse> createSupplementRecord(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(
                schema = @Schema(implementation = SaveSupplementRecordRequest.class)))
        @RequestBody @Valid SaveSupplementRecordRequest request) {
        SaveSupplementRecordResponse response = supplementRecordService.saveSupplementRecord(userId, request);
        URI uri = URI.create("/api/v1/supplement/intake" + LocalDate.now());

        return ResponseEntity.created(uri).body(SuccessResponse.of(201, response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> modifySupplementRecord(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long recordId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(
                schema = @Schema(implementation = UpdateSupplementRecordRequest.class)))
        @RequestBody @Valid UpdateSupplementRecordRequest request) {
        UpdateSupplementRecordResponse response = supplementRecordService
            .updateSupplementRecord(userId, recordId, request);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteSupplementRecord(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long recordId) {
        supplementRecordService.deleteSupplementRecord(userId, recordId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getSupplementRecord(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @RequestParam(name = "date") String date) {
        GetDailySupplementResponse response = supplementRecordService.getSupplementRecord(userId, date);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }
}
