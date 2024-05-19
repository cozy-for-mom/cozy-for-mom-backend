package com.juju.cozyformombackend3.global.auth.service.registration;

import org.springframework.stereotype.Component;

import com.juju.cozyformombackend3.global.auth.model.OAuth2Registration;
import com.juju.cozyformombackend3.global.auth.model.OAuth2UserInfo;
import com.juju.cozyformombackend3.global.auth.service.registration.client.AppleApiClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AppleOAuth2Strategy implements OAuth2Strategy {
    private final AppleApiClient appleApiClient;

    @Override
    public OAuth2Registration getOAuth2Registration() {
        return OAuth2Registration.APPLE;
    }

    @Override
    public OAuth2UserInfo getUserInfo(final String accessValue) {
        // 1. publicKey 발급 받기
        AppleOAuthPublicKeyDto publicKey = appleApiClient.getOAuthPublicKey();

        // 2. publicKey로 identity token을 verify

        // 3. identity token에서 email, profileImageUrl, id 추출

        // 5. client secret 생성

        // 6. client secret으로 refresh token 발급

        // 4. 추출한 정보로 OAuth2UserInfo 객체 생성

        // log.info(response.toString());
        return OAuth2UserInfo.of(response.getEmail(), response.getProfileImageUrl(), response.getRefreshToken());
    }
}
