package com.juju.cozyformombackend3.domain.communitylog.cozylog.controller;

import java.net.URI;

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

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.CozyLogSort;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.CreateCozyLogRequest;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.ModifyCozyLogRequest;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response.CozyLogDetailResponse;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response.CreateCozyLogResponse;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response.GetCozyLogListResponse;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response.ModifyCozyLogResponse;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response.SearchCozyLogResponse;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.service.CozyLogService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cozy-log")
@RequiredArgsConstructor
public class CozyLogController {
    private final CozyLogService cozyLogService;

    @PostMapping
    public ResponseEntity<SuccessResponse> createCozyLog(
        @LoginUserId Long userId,
        @RequestBody CreateCozyLogRequest request) {
        Long createdCozyLogId = cozyLogService.saveCozyLog(userId, request);
        URI location = URI.create("/api/v1/cozy-log/" + createdCozyLogId);

        return ResponseEntity.created(location)
            .body(SuccessResponse.of(201, CreateCozyLogResponse.of(createdCozyLogId)));
    }

    @PutMapping
    public ResponseEntity<SuccessResponse> modifyCozyLog(
        @LoginUserId Long userId,
        @RequestBody ModifyCozyLogRequest request) {
        Long modifiedCozyLogId = cozyLogService.updateCozyLog(userId, request);

        return ResponseEntity.ok(SuccessResponse.of(200, ModifyCozyLogResponse.of(modifiedCozyLogId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCozyLog(
        @LoginUserId Long userId,
        @PathVariable("id") Long removeCozyLogId) {
        cozyLogService.deleteCozyLog(userId, removeCozyLogId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> cozyLogDetail(
        @LoginUserId Long userId,
        @PathVariable("id") Long cozyLogId) {
        CozyLogDetailResponse response = cozyLogService.findCozyLogDetail(userId, cozyLogId);

        return ResponseEntity.ok(SuccessResponse.of(200, response));
    }

    @GetMapping("/list")
    public ResponseEntity<SuccessResponse> cozyLogList(
        @RequestParam(value = "lastId", defaultValue = "0") Long reportId,
        @RequestParam(value = "size", defaultValue = "10") Long size,
        @RequestParam(value = "sort", defaultValue = "lately") CozyLogSort sort
    ) {
        GetCozyLogListResponse response = cozyLogService.findCozyLogList(reportId, size, sort);

        return ResponseEntity.ok(SuccessResponse.of(200, response));
    }

    @GetMapping("/search")
    public ResponseEntity<SuccessResponse> searchCozyLog(
        @LoginUserId Long userId,
        @RequestParam(value = "lastId", defaultValue = "0") Long lastLogId,
        @RequestParam(value = "size", defaultValue = "10") Long size,
        @RequestParam(value = "keyword", defaultValue = "") String keyword,
        @RequestParam(value = "sort", defaultValue = "lately") CozyLogSort sort
    ) {
        SearchCozyLogResponse response = cozyLogService.searchCozyLog(userId,
            CozyLogSearchCondition.of(lastLogId, size, keyword, sort));

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }
}
