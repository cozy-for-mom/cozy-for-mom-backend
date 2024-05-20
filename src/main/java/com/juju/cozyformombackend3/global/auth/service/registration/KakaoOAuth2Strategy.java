package com.juju.cozyformombackend3.global.auth.service.registration;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.juju.cozyformombackend3.global.auth.error.AuthErrorCode;
import com.juju.cozyformombackend3.global.auth.model.OAuth2Registration;
import com.juju.cozyformombackend3.global.auth.model.OAuth2UserInfo;
import com.juju.cozyformombackend3.global.auth.service.registration.client.KakaoApiClient;
import com.juju.cozyformombackend3.global.error.exception.AuthException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoOAuth2Strategy implements OAuth2Strategy {
    private final KakaoApiClient kakaoApiClient;
    private final KakaoAuthProperties kakaoAuthProperties;

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

    @Override
    public boolean unlinkOAuth2Account(final String oauthValue) {
        final String appAdminKey = "KakaoAK " + kakaoAuthProperties.getAppAdminKey();
        final MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("target_id_type", "user_id");
        requestBody.add("target_id", oauthValue);

        ResponseEntity<UnlinkOAuth2AccountDto.KakaoResponse> response = kakaoApiClient.unlinkAccount(
            MediaType.APPLICATION_FORM_URLENCODED_VALUE, appAdminKey, requestBody);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Kakao account unlink success response {}", response.getBody().getId());
            return true;
        } else {
            log.error("Kakao account unlink failed response {}", response.getBody());
            throw new AuthException(AuthErrorCode.SERVER_ERROR_UNLINK_FAILED);
        }
    }
}
