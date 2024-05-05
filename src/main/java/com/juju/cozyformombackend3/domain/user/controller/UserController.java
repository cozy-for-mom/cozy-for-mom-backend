package com.juju.cozyformombackend3.domain.user.controller;

import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.user.dto.SignUpDto;
import com.juju.cozyformombackend3.domain.user.service.UserService;
import com.juju.cozyformombackend3.global.auth.filter.JwtFilter;
import com.juju.cozyformombackend3.global.auth.service.token.TokenProvider;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse> signUp(@Valid @RequestBody SignUpDto.Request request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = authentication.getCredentials().toString();
        Map<String, String> guestInfo = tokenProvider.getInfo(token);

        final SignUpDto.SignUpInfo response = userService.registerUser(guestInfo, request);
        final URI location = URI.create("/api/v1/me");
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + response.getToken());
        httpHeaders.setLocation(location);

        return new ResponseEntity<>(SuccessResponse.of(HttpStatus.CREATED.value(), response.getResponse()), httpHeaders,
            HttpStatus.CREATED);
    }

}
