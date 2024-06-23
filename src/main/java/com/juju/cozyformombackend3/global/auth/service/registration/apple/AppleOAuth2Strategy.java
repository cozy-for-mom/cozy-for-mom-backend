package com.juju.cozyformombackend3.global.auth.service.registration.apple;

import com.juju.cozyformombackend3.global.auth.error.AuthErrorCode;
import com.juju.cozyformombackend3.global.auth.model.OAuth2Registration;
import com.juju.cozyformombackend3.global.auth.model.OAuth2UserInfo;
import com.juju.cozyformombackend3.global.auth.service.registration.OAuth2Strategy;
import com.juju.cozyformombackend3.global.auth.service.registration.apiclient.AppleApiClient;
import com.juju.cozyformombackend3.global.auth.service.registration.apiclient.dto.AppleOAuthPublicKeyDto;
import com.juju.cozyformombackend3.global.auth.service.token.AppleTokenProvider;
import com.juju.cozyformombackend3.global.error.exception.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class AppleOAuth2Strategy implements OAuth2Strategy {
    private final AppleApiClient appleApiClient;
    private final AppleTokenProvider appleTokenProvider;
    private final AppleAuthProperties appleAuthProperties;

    @Override
    public OAuth2Registration getOAuth2Registration() {
        return OAuth2Registration.APPLE;
    }

    @Override
    public OAuth2UserInfo getUserInfo(final String accessValue) {
        AppleOAuthPublicKeyDto publicKeys = appleApiClient.getOAuthPublicKey();

        AppleToken.Response response = getAppleToken(accessValue);

        final String refreshToken = response.getRefreshToken();
        final String email = appleTokenProvider.getEmail(publicKeys, response.getIdToken());

        return OAuth2UserInfo.of(email, null, refreshToken);
    }

    private AppleToken.Response getAppleToken(String accessValue) {
        final String clientSecret = appleTokenProvider.generateClientSecret();
        AppleToken.Request request = AppleToken.Request
                .of(accessValue, appleAuthProperties.getClientId(), clientSecret, "authorization_code");

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("code", request.getCode());
        requestBody.add("client_id", request.getClient_id());
        requestBody.add("client_secret", request.getClient_secret());
        requestBody.add("grant_type", request.getGrant_type());
        AppleToken.Response response = appleApiClient.getToken("application/x-www-form-urlencoded", requestBody);

        return response;
    }

    @Override
    public boolean unlinkOAuth2Account(String oauthValue) {

        final MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", appleAuthProperties.getClientId());
        requestBody.add("client_secret", appleTokenProvider.generateClientSecret());
        requestBody.add("token", oauthValue);

        log.info("Apple account unlink request {}", requestBody);
        final ResponseEntity response = appleApiClient.unlinkAccount(
                MediaType.APPLICATION_FORM_URLENCODED_VALUE, requestBody);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Kakao account unlink success response {}", response.getBody());
            return true;
        } else {
            log.error("Kakao account unlink failed response {}", response.getBody());
            throw new AuthException(AuthErrorCode.SERVER_ERROR_UNLINK_FAILED);
        }
    }
}
