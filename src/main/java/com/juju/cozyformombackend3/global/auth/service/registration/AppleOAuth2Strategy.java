package com.juju.cozyformombackend3.global.auth.service.registration;

import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.juju.cozyformombackend3.global.auth.model.OAuth2Registration;
import com.juju.cozyformombackend3.global.auth.model.OAuth2UserInfo;
import com.juju.cozyformombackend3.global.auth.service.registration.client.AppleApiClient;
import com.juju.cozyformombackend3.global.auth.service.token.AppleTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
        // 1. publicKey 발급 받기
        log.info("1. publicKey 발급 받기");
        AppleOAuthPublicKeyDto publicKeys = appleApiClient.getOAuthPublicKey();
        // 2. publicKey로 identity token을 verify
        // Claims identityTokenClaims = appleTokenProvider.getClaimsBy(identityToken);

        // 3. identity token에서 email, profileImageUrl, id 추출

        // 5. client secret 생성
        log.info("publicKeys: {}", publicKeys);
        log.info("2. client secret 생성");
        final String clientSecret = appleTokenProvider.generateClientSecret();
        log.info("clientSecret: {}", clientSecret);

        // 6. client secret으로 refresh token 발급
        log.info("3. client secret으로 refresh token 발급");
        AppleToken.Request request = AppleToken.Request.of(accessValue, appleAuthProperties.getClientId(), clientSecret,
            "authorization_code");
        log.info("refreshtoken request: {}", request);
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("code", request.getCode());
        requestBody.add("client_id", request.getClient_id());
        requestBody.add("client_secret", request.getClient_secret());
        requestBody.add("grant_type", request.getGrant_type());

        AppleToken.Response response = appleApiClient.getToken("application/x-www-form-urlencoded", requestBody);
        log.info("refreshtoken response: {}", response);
        // 4. 정보 추출하여 OAuth2UserInfo 객체 생성
        final String email = appleTokenProvider.getEmail(publicKeys, response.getIdToken());
        log.info("email: {}", email);
        final String refreshToken = response.getRefreshToken();

        log.info(response.toString());
        return OAuth2UserInfo.of(email, null, refreshToken);
    }
}
