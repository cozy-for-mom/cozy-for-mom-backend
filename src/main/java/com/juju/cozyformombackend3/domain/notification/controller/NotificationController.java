package com.juju.cozyformombackend3.domain.notification.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.notification.dto.CreateExaminationNotification;
import com.juju.cozyformombackend3.domain.notification.dto.CreateRecordNotification;
import com.juju.cozyformombackend3.domain.notification.dto.ModifyRecordNotification;
import com.juju.cozyformombackend3.domain.notification.dto.ModifyRecordNotificationActive;
import com.juju.cozyformombackend3.domain.notification.service.NotificationService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/record")
    public ResponseEntity<SuccessResponse> createRecordNotification(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @RequestBody @Valid CreateRecordNotification.Request request) {
        CreateRecordNotification.Response response = notificationService.saveRecordNotification(userId, request);
        final URI location = URI.create("/api/v1/notificaion/record");

        return ResponseEntity.created(location).body(SuccessResponse.of(201, response));
    }

    @PutMapping("/record/{id}")
    public ResponseEntity<SuccessResponse> modifyRecordNotification(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long notificationId,
        @RequestBody @Valid ModifyRecordNotification.Request request) {
        ModifyRecordNotification.Response response = notificationService
            .modifyRecordNotification(notificationId, request);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @PutMapping("/record/active/{id}")
    public ResponseEntity<SuccessResponse> modifyRecordNotificationActive(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long notificationId,
        @RequestBody @Valid ModifyRecordNotificationActive.Request request) {
        ModifyRecordNotificationActive.Response response = notificationService
            .modifyRecordNotificationActive(notificationId, request);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @DeleteMapping("/record/{id}")
    public ResponseEntity<Void> removeRecordNotification(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long notificationId) {
        notificationService.removeRecordNotification(notificationId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/examination")
    public ResponseEntity<SuccessResponse> createExaminationNotification(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @RequestBody @Valid CreateExaminationNotification.Request request) {
        CreateExaminationNotification.Response response = notificationService
            .saveExaminationNotification(userId, request);
        final URI location = URI.create("/api/v1/notificaion/examination");

        return ResponseEntity.created(location).body(SuccessResponse.of(201, response));
    }
}
