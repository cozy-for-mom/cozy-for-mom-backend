package com.juju.cozyformombackend3.domain.userlog.supplement.controller;

import java.net.URI;
import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.userlog.supplement.dto.request.RegisterSupplementRequest;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.request.UpdateSupplementRequest;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.response.RegisterSupplementResponse;
import com.juju.cozyformombackend3.domain.userlog.supplement.dto.response.UpdateSupplementResponse;
import com.juju.cozyformombackend3.domain.userlog.supplement.service.SupplementService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/supplement")
public class SupplementController {

    private final SupplementService supplementService;

    @PostMapping
    public ResponseEntity<SuccessResponse> registerSupplement(
        @LoginUserId Long userId,
        @RequestBody @Valid RegisterSupplementRequest request) {
        RegisterSupplementResponse response = supplementService.registerSupplement(userId, request);
        URI uri = URI.create("/api/v1/supplement/intake" + LocalDate.now());

        return ResponseEntity.created(uri).body(SuccessResponse.of(201, response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> modifySupplement(
        @LoginUserId Long userId,
        @PathVariable(name = "id") Long supplementId,
        @RequestBody @Valid UpdateSupplementRequest request) {
        UpdateSupplementResponse response = supplementService.updateSupplement(supplementId, request);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> removeSupplement(
        @LoginUserId Long userId,
        @PathVariable(name = "id") Long supplementId) {
        supplementService.deleteSupplement(supplementId);

        return ResponseEntity.noContent().build();
    }

}
