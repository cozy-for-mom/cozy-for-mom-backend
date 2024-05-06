package com.juju.cozyformombackend3.domain.notification.controller;

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

import com.juju.cozyformombackend3.domain.notification.controller.dto.CreateExaminationNotification;
import com.juju.cozyformombackend3.domain.notification.controller.dto.CreateRecordNotification;
import com.juju.cozyformombackend3.domain.notification.controller.dto.GetRecordNotificationList;
import com.juju.cozyformombackend3.domain.notification.controller.dto.ModifyExaminationNotification;
import com.juju.cozyformombackend3.domain.notification.controller.dto.ModifyRecordNotification;
import com.juju.cozyformombackend3.domain.notification.controller.dto.ModifyRecordNotificationActive;
import com.juju.cozyformombackend3.domain.notification.model.NotificationCategory;
import com.juju.cozyformombackend3.domain.notification.service.NotificationService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/record")
    public ResponseEntity<SuccessResponse> getRecordNotificationList(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @RequestParam(value = "type", defaultValue = "supplement") NotificationCategory notificationCategory) {

        GetRecordNotificationList.Response response = notificationService
            .getRecordNotificationList(userId, notificationCategory);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @PostMapping("/record")
    public ResponseEntity<SuccessResponse> createRecordNotification(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(
                schema = @Schema(implementation = CreateRecordNotification.Request.class)))
        @RequestBody @Valid CreateRecordNotification.Request request) {
        CreateRecordNotification.Response response = notificationService.saveRecordNotification(userId, request);
        final URI location = URI.create("/api/v1/notificaion/record");

        return ResponseEntity.created(location).body(SuccessResponse.of(201, response));
    }

    @PutMapping("/record/{id}")
    public ResponseEntity<SuccessResponse> modifyRecordNotification(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long notificationId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(
                schema = @Schema(implementation = ModifyRecordNotification.Request.class)))
        @RequestBody @Valid ModifyRecordNotification.Request request) {
        ModifyRecordNotification.Response response = notificationService
            .modifyRecordNotification(notificationId, request);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @PutMapping("/record/active/{id}")
    public ResponseEntity<SuccessResponse> modifyRecordNotificationActive(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long notificationId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(
                schema = @Schema(implementation = ModifyRecordNotificationActive.Request.class)))
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
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(
                schema = @Schema(implementation = CreateExaminationNotification.Request.class)))
        @RequestBody @Valid CreateExaminationNotification.Request request) {
        CreateExaminationNotification.Response response = notificationService
            .saveExaminationNotification(userId, request);
        final URI location = URI.create("/api/v1/notificaion/examination");

        return ResponseEntity.created(location).body(SuccessResponse.of(201, response));
    }

    @PutMapping("/examination/{id}")
    public ResponseEntity<SuccessResponse> modifyExaminationNotification(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long notificationId,
        @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
            content = @Content(
                schema = @Schema(implementation = ModifyExaminationNotification.Request.class)))
        @RequestBody @Valid ModifyExaminationNotification.Request request) {
        ModifyExaminationNotification.Response response = notificationService
            .modifyExaminationNotification(userId, notificationId, request);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @DeleteMapping("/examination/{id}")
    public ResponseEntity<Void> removeExaminationNotification(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long notificationId) {
        notificationService.removeExaminationNotification(notificationId);

        return ResponseEntity.noContent().build();
    }
}
