package com.juju.cozyformombackend3.domain.bloodsugar.controller;

import com.juju.cozyformombackend3.domain.bloodsugar.dto.request.ModifyBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.request.SaveBloodSugarRecordRequest;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.response.FindBloodSugarListResponse;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.response.FindDailyBloodSugarListResponse;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.response.ModifyBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.bloodsugar.dto.response.SaveBloodSugarRecordResponse;
import com.juju.cozyformombackend3.domain.bloodsugar.service.BloodSugarService;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/bloodsugar")
public class BloodSugarController {

    private final BloodSugarService bloodSugarService;

    @PostMapping
    public ResponseEntity<SuccessResponse> saveBloodSugarRecord(@RequestBody SaveBloodSugarRecordRequest request) {
        User user = new User();

        SaveBloodSugarRecordResponse response = bloodSugarService.saveBloodSugarRecord(request, user);
        URI uri = URI.create("/api/v1/bloodsugar/" + response.getBloodSugarRecordId());

        return ResponseEntity.created(uri).body(SuccessResponse.of(201, response));
    }

    @PutMapping
    public ResponseEntity<SuccessResponse> modifyBloodSugarRecord(@RequestBody ModifyBloodSugarRecordRequest request) {
        User user = new User();

        ModifyBloodSugarRecordResponse response = bloodSugarService.updateBloodSugarRecord(request, user);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> removeBloodSugarRecord(@PathVariable(name = "id") Long id) {
        User user = new User();

        bloodSugarService.deleteBloodSugarRecord(id, user);

        return ResponseEntity.ok().body(SuccessResponse.of(200, null));
    }

    @GetMapping()
    public ResponseEntity<SuccessResponse> searchDailyBloodSugarRecord(@RequestParam(name = "date") String date) {
        // TODO: 2021-10-04 date validation
        FindDailyBloodSugarListResponse response = bloodSugarService.findDailyBloodSugarRecord(1L, date);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @GetMapping("/period")
    public ResponseEntity<SuccessResponse> searchBloodSugarRecord(@RequestParam(name = "date") String date,
                                                                  @RequestParam(name = "type") String type,
                                                                  Pageable pageable) {
        FindBloodSugarListResponse response = bloodSugarService.findBloodSugarRecord(1L, date, type, pageable);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }
}
