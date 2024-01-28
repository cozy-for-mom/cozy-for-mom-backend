package com.juju.cozyformombackend3.domain.supplement.controller;

import com.juju.cozyformombackend3.domain.supplement.dto.request.DeleteSupplementRecordRequest;
import com.juju.cozyformombackend3.domain.supplement.dto.request.SaveSupplementRecordRequest;
import com.juju.cozyformombackend3.domain.supplement.dto.response.GetDailySupplementResponse;
import com.juju.cozyformombackend3.domain.supplement.dto.response.SaveSupplementRecordResponse;
import com.juju.cozyformombackend3.domain.supplement.service.SupplementRecordService;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.dto.request.ListRequest;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/supplement/intake")
public class SupplementRecordController {

    private final SupplementRecordService supplementRecordService;

    @PostMapping("")
    public ResponseEntity<SuccessResponse> createSupplementRecord(@RequestBody SaveSupplementRecordRequest request) {
        Long userId = 1L;
        User user = new User();

        SaveSupplementRecordResponse response = supplementRecordService.saveSupplementRecord(request, user);
        URI uri = URI.create("/api/v1/supplement/intake/" + response.getSupplementRecordId());

        return ResponseEntity.created(uri).body(SuccessResponse.of(201, response));
    }

    @DeleteMapping("")
    public ResponseEntity<SuccessResponse> deleteSupplementRecord(
            @RequestBody ListRequest<DeleteSupplementRecordRequest> request) {
        User user = new User();

        supplementRecordService.deleteSupplementRecord(request, user);
        return ResponseEntity.ok().body(SuccessResponse.of(200, "success"));
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getSupplementRecord(@RequestParam(name = "date") String date) {
        GetDailySupplementResponse response = supplementRecordService.getSupplementRecord(1L, date);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }
}
