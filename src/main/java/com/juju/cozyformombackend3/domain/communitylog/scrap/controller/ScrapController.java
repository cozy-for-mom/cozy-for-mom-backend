package com.juju.cozyformombackend3.domain.communitylog.scrap.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.communitylog.scrap.dto.request.ApplyScrapRequest;
import com.juju.cozyformombackend3.domain.communitylog.scrap.dto.request.UnscrapListRequest;
import com.juju.cozyformombackend3.domain.communitylog.scrap.dto.response.FindScrapListResponse;
import com.juju.cozyformombackend3.domain.communitylog.scrap.dto.response.ScrapResponse;
import com.juju.cozyformombackend3.domain.communitylog.scrap.service.ScrapService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/scrap")
@RequiredArgsConstructor
public class ScrapController {
    private final ScrapService scrapService;

    @PostMapping()
    public ResponseEntity<SuccessResponse> scrapSave(
        @LoginUserId Long userId,
        @RequestBody @Valid ApplyScrapRequest request) {
        ScrapResponse response = scrapService.saveScrap(userId, request);
        URI location = URI.create("/api/v1/cozy-log/" + request.getCozyLogId());

        return ResponseEntity.created(location).body(SuccessResponse.of(201, response));
    }

    @PostMapping("/unscraps")
    public ResponseEntity<Void> unscrapList(@LoginUserId Long userId,
        @RequestBody UnscrapListRequest request) {
        scrapService.deleteScrapList(userId, request);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getScrapList(
        @LoginUserId Long userId,
        @RequestParam(value = "lastId", defaultValue = "0") Long reportId,
        @RequestParam(value = "size", defaultValue = "10") Long size) {
        FindScrapListResponse response = scrapService.findScrapList(userId, reportId, size);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

}
