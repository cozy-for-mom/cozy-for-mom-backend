package com.juju.cozyformombackend3.global.auth.service.registration;

import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import com.juju.cozyformombackend3.domain.user.model.OAuth2Registration;
import com.juju.cozyformombackend3.global.auth.model.OAuth2UserInfo;
import com.juju.cozyformombackend3.global.auth.model.UserRole;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoOAuth2Strategy implements OAuth2Strategy {
    @Override
    public OAuth2Registration getOAuth2Registration() {
        return OAuth2Registration.KAKAO;
    }

    @Override
    public OAuth2UserInfo getUserInfo(final OAuth2User user) {
        final Map<String, Object> attributes = user.getAttributes();
        log.info(attributes.toString());
        final String oauthId = String.valueOf(attributes.get("id"));
        final String email = ((Map<String, String>)attributes.get("kakao_account")).get("email");
        final String profileImage = String.valueOf(attributes.get("profile_image"));
        final String nickname = ((Map<String, String>)attributes.get("properties")).get("nickname");
        log.info("{}, {}, {}, {}", oauthId, email, profileImage, nickname);

        isEmailExist(email);
        return OAuth2UserInfo.of(oauthId, email, profileImage, nickname, UserRole.USER);
    }
}
