package com.juju.cozyformombackend3.global.auth.service.registration.client;

import org.springframework.web.service.annotation.GetExchange;

import com.juju.cozyformombackend3.global.auth.service.registration.AppleOAuthPublicKeyDto;

public interface AppleApiClient {
    // @GetExchange("https://")
    // KakaoDto getUserInfo(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token);

    @GetExchange("https://appleid.apple.com/auth/keys")
    AppleOAuthPublicKeyDto getOAuthPublicKey();

}
