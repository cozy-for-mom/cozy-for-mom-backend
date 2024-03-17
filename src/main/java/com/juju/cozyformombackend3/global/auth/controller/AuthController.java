package com.juju.cozyformombackend3.global.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
@Controller
public class AuthController {

    @GetMapping("/login/kakao")
    public String kakaoLogin() {
        return "login-kakao";
    }
}
