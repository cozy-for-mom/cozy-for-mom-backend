package com.juju.cozyformombackend3.domain.babylog.growth.controller;

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

import com.juju.cozyformombackend3.domain.babylog.growth.controller.dto.FindGrowth;
import com.juju.cozyformombackend3.domain.babylog.growth.controller.dto.FindGrowthList;
import com.juju.cozyformombackend3.domain.babylog.growth.controller.dto.SaveGrowth;
import com.juju.cozyformombackend3.domain.babylog.growth.controller.dto.UpdateGrowth;
import com.juju.cozyformombackend3.domain.babylog.growth.service.GrowthService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/growth")
public class GrowthController {

    private final GrowthService growthService;

    @Operation(
        summary = "성장 기록 등록",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "someExample2",
                        value = "{\"babyProfileId\":10,\"date\":\"2023-10-14\",\"growthImageUrl\":\"skjfhalskjdhfaslkjfhlk\",\"title\":\"제목\",\"content\":\"오늘 아롱이랑 다롱이를 보고왔는데 너무 예뻤어 선생님이 너무 잘 움직이고 있대 아프지 말고 무럭무럭 자라라\",\"babies\":[{\"babyId\":7,\"growthInfo\":{\"weight\":148.5,\"headDiameter\":3.4,\"headCircum\":12.4,\"abdomenCircum\":11.11,\"thighLength\":2.3}},{\"babyId\":8,\"growthInfo\":{\"weight\":148.5,\"headDiameter\":3.4,\"headCircum\":12.4,\"abdomenCircum\":11.11,\"thighLength\":2.3}}]}"
                    )
                }
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "생성 완료")
        }
    )
    @PostMapping
    public ResponseEntity<SuccessResponse> createGrowth(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @RequestBody @Valid SaveGrowth.Request request) {

        SaveGrowth.Response response = growthService.saveGrowth(userId, request);
        URI uri = URI.create("/api/v1/growth/" + response.getGrowthDiaryId());

        return ResponseEntity.created(uri).body(SuccessResponse.of(201, response));
    }

    @Operation(
        summary = "성장 기록 수정",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                examples = {
                    @ExampleObject(
                        name = "someExample1",
                        value = "{\"growthDairyId\":1,\"babyProfileId\":1,\"date\":\"2023-10-14\",\"growthImageUrl\":\"skjfhalskjdhfaslkjfhlk\",\"title\":\"제목\",\"content\":\"오늘 아롱이랑 다롱이를 보고왔는데 너무 예뻤어 선생님이 너무 잘 움직이고 있대 아프지 말고 무럭무럭 자라라\",\"babies\":[{\"growthReportId\":1,\"babyId\":1,\"growthInfo\":{\"weight\":148.5,\"headDiameter\":3.4,\"headCircum\":12.4,\"abdomenCircum\":11.11,\"thighLength\":2.3}},{\"growthReportId\":2,\"babyId\":2,\"growthInfo\":{\"weight\":148.5,\"headDiameter\":3.4,\"headCircum\":12.4,\"abdomenCircum\":11.11,\"thighLength\":2.3}}]}"
                    )}
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "수정 완료")
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> modifyGrowth(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long reportId,
        @RequestBody @Valid UpdateGrowth.Request request) {
        UpdateGrowth.Response response = growthService.updateGrowth(userId, reportId, request);

        return ResponseEntity.ok(SuccessResponse.of(200, response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> removeGrowth(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long reportId) {
        growthService.deleteGrowth(userId, reportId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getGrowth(
        @Parameter(hidden = true) @LoginUserId Long userId,
        @PathVariable(name = "id") Long reportId) {
        FindGrowth.Response response = growthService.getGrowth(userId, reportId);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }

    @GetMapping("/{babyProfileId}/board")
    public ResponseEntity<SuccessResponse> getGrowthList(
        @PathVariable(name = "babyProfileId") Long babyProfileId,
        @RequestParam(value = "lastId", defaultValue = "0") Long reportId,
        @RequestParam(value = "size", defaultValue = "10") Long size
    ) {
        FindGrowthList.Response response = growthService.getGrowthList(babyProfileId, reportId, size);

        return ResponseEntity.ok().body(SuccessResponse.of(200, response));
    }
}
