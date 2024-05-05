package com.juju.cozyformombackend3.global.auth.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.global.auth.dto.CheckNicknameDto;
import com.juju.cozyformombackend3.global.auth.filter.JwtFilter;
import com.juju.cozyformombackend3.global.auth.model.OAuth2UserInfo;
import com.juju.cozyformombackend3.global.auth.service.AuthService;
import com.juju.cozyformombackend3.global.auth.service.token.TokenProvider;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/authenticate")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    // @PostMapping
    // public ResponseEntity<SuccessResponse> signIn(@Valid @RequestBody SignInDto.Request request) {
    //     String accessToken = authService.authenticate(request);
    //
    //     HttpHeaders httpHeaders = new HttpHeaders();
    //     httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);
    //
    //     return new ResponseEntity<>(SuccessResponse.of(HttpStatus.OK.value(), null), httpHeaders, HttpStatus.OK);
    // }

    @PostMapping("/token/guest")
    public ResponseEntity<SuccessResponse> testGuestToken() {
        OAuth2UserInfo userInfo = OAuth2UserInfo.of("shsh1318@cozy.com", null, "shsh1318");
        String accessToken = tokenProvider.generateGuestToken(userInfo);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);

        return new ResponseEntity<>(SuccessResponse.of(HttpStatus.OK.value(), null), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/nickname")
    public ResponseEntity<SuccessResponse> checkDuplicateNickname(
        @Valid @RequestBody CheckNicknameDto.Request request) {
        CheckNicknameDto.Response response = authService.checkExistsNickname(request);

        return ResponseEntity.ok().body(SuccessResponse.of(HttpStatus.OK.value(), response));
    }

}
