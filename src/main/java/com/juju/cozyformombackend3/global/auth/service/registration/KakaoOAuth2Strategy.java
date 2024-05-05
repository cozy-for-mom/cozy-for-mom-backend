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

        // OAuth2UserInfo userInfo = requestUserInfo(accessValue);
        // final Map<String, Object> attributes = user.getAttributes();
        // log.info(attributes.toString());
        // final String oauthId = String.valueOf(attributes.get("id"));
        // final String email = c
        // final String profileImage = String.valueOf(attributes.get("profile_image"));
        // final String nickname = ((Map<String, String>)attributes.get("properties")).get("nickname");
        // log.info("{}, {}, {}, {}", oauthId, email, profileImage, nickname);
        // isEmailExist(email);

        // OAuth2UserInfo.of(email, profileImage, oauthId);
    }
}
