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
import com.juju.cozyformombackend3.domain.notification.controller.dto.NotiTestRequest;
import com.juju.cozyformombackend3.domain.notification.model.NotificationCategory;
import com.juju.cozyformombackend3.domain.notification.service.APNsService;
import com.juju.cozyformombackend3.domain.notification.service.NotificationService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/notification")
@Slf4j
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final APNsService apnsService;

    @Operation(
        summary = "알림 test",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "token에는 전송 대상의 device token 넣으면 됨요",
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "noti test",
                        value = "{\"aps\":{\"alert\":{\"title\":\"제목\",\"subtitle\":\"부제목\",\"body\":\"본문\"},\"category\":\"NOTIFICATION_TYPE\"}}"
                    )
                }
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "완료")
        }
    )
    @PostMapping("/apns/test")
    public ResponseEntity<SuccessResponse> apnsPushTest(
        @RequestParam(value = "token") String token,
        @RequestBody NotiTestRequest request) {
        log.info("token: {}", token);
        apnsService.pushTestToAPNs(token, request);
        return ResponseEntity.ok().body(SuccessResponse.of(200, "푸시 발송 완료"));
    }

    @GetMapping("/record")
    public ResponseEntity<SuccessResponse> getRecordNotificationList(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @RequestParam(value = "type", defaultValue = "supplement") NotificationCategory notificationCategory) {

        System.out.println("userId = " + userId);
        GetRecordNotificationList.Response response = notificationService
            .getRecordNotificationList(userId, notificationCategory);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @Operation(
        summary = "혈당/영양제 알람 등록",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "notifyAt: { “one hour ago”, “thirty minutes ago”, “on time” }"
                          + "daysOfWeek: { mon, tue, wed, thu, fri, sat, sun, all}",
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "supplement",
                        value = "{\"type\":\"supplement\",\"title\":\"철분 먹을 시간\",\"notifyAt\":[\"one hour ago\"],\"targetTimeAt\":[\"18:00\"],\"daysOfWeek\":[\"mon\",\"wed\",\"fri\"]}"
                    )
                }
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "생성 완료")
        }
    )
    @PostMapping("/record")
    public ResponseEntity<SuccessResponse> createRecordNotification(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @RequestBody @Valid CreateRecordNotification.Request request) {
        CreateRecordNotification.Response response = notificationService.saveRecordNotification(userId, request);
        final URI location = URI.create("/api/v1/notificaion/record");

        return ResponseEntity.created(location).body(SuccessResponse.of(201, response));
    }

    @Operation(
        summary = "혈당/영양제 알람 수정",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "notifyAt: { “one hour ago”, “thirty minutes ago”, “on time” }"
                          + "daysOfWeek: { mon, tue, wed, thu, fri, sat, sun, all}",
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "someExample3",
                        value = "{\"title\":\"철분 먹을 시간\",\"notifyAt\":[\"one hour ago\"],\"targetTimeAt\":[\"18:00\"],\"daysOfWeek\":[\"mon\",\"wed\",\"fri\"]}"
                    )
                }
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "생성 완료")
        }
    )
    @PutMapping("/record/{id}")
    public ResponseEntity<SuccessResponse> modifyRecordNotification(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long notificationId,
        @RequestBody @Valid ModifyRecordNotification.Request request) {
        ModifyRecordNotification.Response response = notificationService
            .modifyRecordNotification(notificationId, request);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @Operation(
        summary = "혈당/영양제 알람 활성화 상태 수정",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "someExample3",
                        value = "{\"isActive\":\"false\"}"
                    )
                }
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "수정 완료")
        }
    )
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

    @Operation(
        summary = "검진일 알람 생성",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "notifyAt: { “on day”, “one day ago”, “two day ago”, “one week ago”, “none” }",
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "someExample4",
                        value = "{\"babyProfileId\":4,\"examinationAt\":\"2024-04-30\",\"notifyAt\":[\"on day\",\"one week ago\"]}"
                    )
                }
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "생성 완료")
        }
    )
    @PostMapping("/examination")
    public ResponseEntity<SuccessResponse> createExaminationNotification(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @RequestBody @Valid CreateExaminationNotification.Request request) {
        CreateExaminationNotification.Response response = notificationService
            .saveExaminationNotification(userId, request);
        final URI location = URI.create("/api/v1/notificaion/examination");

        return ResponseEntity.created(location).body(SuccessResponse.of(201, response));
    }

    @Operation(
        summary = "검진일 알람 수정",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "notifyAt: { “on day”, “one day ago”, “two day ago”, “one week ago”, “none” }",
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "someExample",
                        value = "{\"examinationAt\":\"2024-04-30\",\"notifyAt\":[\"on day\",\"one week ago\"]}"
                    )
                }
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "수정 완료")
        }
    )
    @PutMapping("/examination/{id}")
    public ResponseEntity<SuccessResponse> modifyExaminationNotification(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long notificationId,
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
