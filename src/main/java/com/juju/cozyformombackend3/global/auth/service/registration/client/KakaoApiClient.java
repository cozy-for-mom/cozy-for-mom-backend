package com.juju.cozyformombackend3.global.auth.service.registration.client;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;

import com.juju.cozyformombackend3.global.auth.service.registration.KakaoDto;

public interface KakaoApiClient {
    @GetExchange("https://kapi.kakao.com/v2/user/me")
    KakaoDto getUserInfo(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token);
}
