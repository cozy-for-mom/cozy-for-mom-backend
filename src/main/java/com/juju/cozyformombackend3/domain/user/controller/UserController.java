package com.juju.cozyformombackend3.domain.user.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juju.cozyformombackend3.domain.user.dto.SignUpDto;
import com.juju.cozyformombackend3.domain.user.service.UserService;
import com.juju.cozyformombackend3.global.dto.response.SuccessResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse> signUp(@Valid @RequestBody SignUpDto.Request request) {
        System.out.println("whduhw");
        final SignUpDto.Response response = userService.registerUser(request);
        final URI location = URI.create("/api/v1/me");

        return ResponseEntity.created(location).body(SuccessResponse.of(HttpStatus.CREATED.value(), response));
    }

}
