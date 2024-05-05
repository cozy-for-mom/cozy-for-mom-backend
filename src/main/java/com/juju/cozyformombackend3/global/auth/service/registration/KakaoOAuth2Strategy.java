package com.juju.cozyformombackend3.global.auth.service.registration;

import org.springframework.stereotype.Component;

import com.juju.cozyformombackend3.global.auth.model.OAuth2Registration;
import com.juju.cozyformombackend3.global.auth.model.OAuth2UserInfo;
import com.juju.cozyformombackend3.global.auth.service.registration.client.KakaoApiClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoOAuth2Strategy implements OAuth2Strategy {
    private final KakaoApiClient kakaoApiClient;

    @Override
    public OAuth2Registration getOAuth2Registration() {
        return OAuth2Registration.KAKAO;
    }

    @Override
    public OAuth2UserInfo getUserInfo(final String accessValue) {
        final String token = "Bearer " + accessValue;
        KakaoDto response = kakaoApiClient.getUserInfo(token);
        log.info(response.toString());
        return OAuth2UserInfo.of(response.getEmail(), response.getProfileImageUrl(), response.getId());
    }
}
