package com.juju.cozyformombackend3.global.auth.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.global.auth.dto.CheckNicknameDto;
import com.juju.cozyformombackend3.global.auth.dto.api.AuthenticateOAuthDto;
import com.juju.cozyformombackend3.global.auth.filter.JwtFilter;
import com.juju.cozyformombackend3.global.auth.service.AuthService;
import com.juju.cozyformombackend3.global.auth.service.token.CozyTokenProvider;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/authenticate")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final CozyTokenProvider cozyTokenProvider;

    @Operation(
        summary = "oauth 인증 및 토큰 발급 (로그인)",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                examples = {
                    @ExampleObject(name = "kakao example", value = """ 
                            { 
                                "oauthType" : "kakao",
                                "value" : "kakaoAccessToken",
                                "deviceToken" : "deviceToken" 
                            } 
                        """),
                    @ExampleObject(name = "apple example", value = """ 
                            { 
                                "oauthType" : "apple",
                                "value" : "apple 인증코드 값",
                                "deviceToken" : "deviceToken" 
                            } 
                        """)}
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "Token 발급 성공"),
            @ApiResponse(responseCode = "409", description = "이미 해당 이메일로 회원가입된 다른 소셜 로그인 계정이 존재함")
        }
    )
    @PostMapping("/oauth")
    public ResponseEntity<SuccessResponse> authenticateOAuth(
        @Valid @RequestBody AuthenticateOAuthDto.Request request) {

        String accessToken = authService.authenticateOAuth(request);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);

        return new ResponseEntity<>(SuccessResponse.of(HttpStatus.OK.value(), null), httpHeaders, HttpStatus.OK);

    }

    @Operation(
        summary = "닉네임 중복 확인",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                examples = {
                    @ExampleObject(name = "someExample1", value = """ 
                            { 
                                "nickname" : "nickname1" 
                            } 
                        """)}
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "중복되지 않음, 사용 가능한 닉네임")
        }
    )
    @PostMapping("/nickname")
    public ResponseEntity<SuccessResponse> checkDuplicateNickname(
        @Valid @RequestBody CheckNicknameDto.Request request) {
        CheckNicknameDto.Response response = authService.checkExistsNickname(request);

        return ResponseEntity.ok().body(SuccessResponse.of(HttpStatus.OK.value(), response));
    }
}
