package com.juju.cozyformombackend3.domain.growth.controller;

import com.juju.cozyformombackend3.domain.growth.dto.request.SaveGrowthRequest;
import com.juju.cozyformombackend3.domain.growth.dto.request.UpdateGrowthRequest;
import com.juju.cozyformombackend3.domain.growth.dto.response.FindGrowthResponse;
import com.juju.cozyformombackend3.domain.growth.dto.response.SaveGrowthResponse;
import com.juju.cozyformombackend3.domain.growth.dto.response.UpdateGrowthResponse;
import com.juju.cozyformombackend3.domain.growth.service.GrowthService;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/growth")
public class GrowthController {

    private final GrowthService growthService;

    @PostMapping
    public ResponseEntity<SuccessResponse> createGrowth(@RequestBody SaveGrowthRequest request) {
        User user = new User();

        SaveGrowthResponse response = growthService.saveGrowth(user, request);
        URI uri = URI.create("/api/v1/growth/" + response.getGrowthDiaryId());

        return ResponseEntity.created(uri).body(SuccessResponse.of(201, response));
    }

    @PutMapping
    public ResponseEntity<SuccessResponse> modifyGrowth(@RequestBody UpdateGrowthRequest request) {
        User user = new User();

        UpdateGrowthResponse response = growthService.updateGrowth(user, request);

        return ResponseEntity.ok(SuccessResponse.of(200, response));
    }

    @DeleteMapping
    public ResponseEntity<SuccessResponse> removeGrowth(
            @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        User user = new User();

        growthService.deleteGrowth(user, date);

        return ResponseEntity.ok(SuccessResponse.of(200, null));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getGrowth(
            @PathVariable(name = "id") Long babyProfileId,
            @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        Long userId = 1L;
        FindGrowthResponse response = growthService.getGrowth(userId, babyProfileId, date);

        return ResponseEntity.ok(SuccessResponse.of(200, response));
    }
}
