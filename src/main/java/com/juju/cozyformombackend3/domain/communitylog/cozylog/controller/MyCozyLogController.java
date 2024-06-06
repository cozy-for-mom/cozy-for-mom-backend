package com.juju.cozyformombackend3.domain.communitylog.cozylog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.controller.condition.CozyLogCondition;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.controller.condition.CozyLogSort;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.controller.dto.DeleteMyCozyLogList;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.controller.dto.FindMyCozyLogList;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.service.CozyLogService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/me/cozy-log")
@RequiredArgsConstructor
public class MyCozyLogController {
    private final CozyLogService cozyLogService;

    @GetMapping
    public ResponseEntity<SuccessResponse> getMyCozyLogList(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @RequestParam(value = "lastId", required = false) Long reportId,
        @RequestParam(value = "size", defaultValue = "10") Long size,
        @RequestParam(value = "sort", defaultValue = "") CozyLogSort sort) {
        FindMyCozyLogList.Response response = cozyLogService.findMyCozyLog(
            CozyLogCondition.builder()
                .userId(userId)
                .lastLogId(reportId)
                .size(size)
                .writerId(userId)
                .sort(sort)
                .build());

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @DeleteMapping
    public ResponseEntity<SuccessResponse> removeMyCozyLogList(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(
                schema = @Schema(implementation = DeleteMyCozyLogList.Request.class)))
        @RequestBody DeleteMyCozyLogList.Request request) {
        cozyLogService.deleteCozyLogList(userId, request);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<SuccessResponse> removeAllMyCozyLog(
        @Parameter(hidden = true) @LoginUserId Long userId) {
        cozyLogService.deleteAllCozyLog(userId);

        return ResponseEntity.noContent().build();
    }
}
