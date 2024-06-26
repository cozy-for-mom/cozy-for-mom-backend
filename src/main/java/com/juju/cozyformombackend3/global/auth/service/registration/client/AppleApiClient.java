package com.juju.cozyformombackend3.global.auth.service.registration.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.juju.cozyformombackend3.global.auth.service.registration.AppleOAuthPublicKeyDto;
import com.juju.cozyformombackend3.global.auth.service.registration.AppleToken;

public interface AppleApiClient {
    @GetExchange("https://appleid.apple.com/auth/keys")
    AppleOAuthPublicKeyDto getOAuthPublicKey();

    @PostExchange("https://appleid.apple.com/auth/token")
    AppleToken.Response getToken(
        @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType,
        @RequestBody MultiValueMap request);

    @PostExchange("https://appleid.apple.com/auth/revoke")
    ResponseEntity unlinkAccount(
        @RequestHeader(HttpHeaders.CONTENT_TYPE) String contentType,
        @RequestBody MultiValueMap request);

}
