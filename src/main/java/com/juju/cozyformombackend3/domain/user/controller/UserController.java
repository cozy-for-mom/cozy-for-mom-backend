package com.juju.cozyformombackend3.domain.user.controller;

import com.juju.cozyformombackend3.domain.user.dto.LogoutDto;
import com.juju.cozyformombackend3.domain.user.dto.SignOutDto;
import com.juju.cozyformombackend3.domain.user.dto.SignUpDto;
import com.juju.cozyformombackend3.domain.user.service.UserService;
import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.auth.filter.JwtFilter;
import com.juju.cozyformombackend3.global.auth.service.token.CozyTokenProvider;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CozyTokenProvider cozyTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse> signUp(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(
                            schema = @Schema(implementation = SignUpDto.Request.class)))
            @Valid @RequestBody SignUpDto.Request request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getCredentials().toString();
        Map<String, String> guestInfo = cozyTokenProvider.getInfo(token);

        final SignUpDto.SignUpInfo response = userService.registerUser(guestInfo, request);
        final URI location = URI.create("/api/v1/me");
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + response.getToken());
        httpHeaders.setLocation(location);

        return new ResponseEntity<>(SuccessResponse.of(HttpStatus.CREATED.value(), response.getResponse()), httpHeaders,
                HttpStatus.CREATED);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<SuccessResponse> logOut(
            @Parameter(hidden = true) @LoginUserId Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getCredentials().toString();
        Long tmpUserId = cozyTokenProvider.getUserId(token);

        LogoutDto.Response response = userService.logout(tmpUserId, token);

        return ResponseEntity.ok(SuccessResponse.of(HttpStatus.OK.value(), response));
    }

    @Operation(
            summary = "성장 기록 등록",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "someExample2",
                                            value = "{\"reason\":\"내 맘이다.\"}"
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "회원탈퇴 완료")
            }
    )
    @DeleteMapping("/signout")
    public ResponseEntity<SuccessResponse> signOut(
            @Parameter(hidden = true) @LoginUserId Long userId,
            @RequestBody SignOutDto.Request request) {

        LogoutDto.Response response = userService.signOut(userId, request.getReason());

        return ResponseEntity.noContent().build();
    }
}
