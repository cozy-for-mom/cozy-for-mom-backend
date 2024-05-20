package com.juju.cozyformombackend3.global.auth.service.registration.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.juju.cozyformombackend3.global.auth.service.registration.KakaoDto;
import com.juju.cozyformombackend3.global.auth.service.registration.UnlinkOAuth2AccountDto;

public interface KakaoApiClient {
    @GetExchange("https://kapi.kakao.com/v2/user/me")
    KakaoDto getUserInfo(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token);

    @PostExchange("https://kapi.kakao.com/v1/user/unlink")
    ResponseEntity<UnlinkOAuth2AccountDto.KakaoResponse> unlinkAccount(
        @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType,
        @RequestHeader(name = HttpHeaders.AUTHORIZATION) String appAdminKey,
        @RequestBody MultiValueMap request);
}
