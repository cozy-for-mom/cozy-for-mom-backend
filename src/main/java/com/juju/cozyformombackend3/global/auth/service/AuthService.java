package com.juju.cozyformombackend3.global.auth.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.global.auth.dto.CheckNicknameDto;
import com.juju.cozyformombackend3.global.auth.dto.api.AuthenticateOAuthDto;
import com.juju.cozyformombackend3.global.auth.error.AuthErrorCode;
import com.juju.cozyformombackend3.global.auth.model.OAuth2UserInfo;
import com.juju.cozyformombackend3.global.auth.service.registration.OAuth2RegistrationComposite;
import com.juju.cozyformombackend3.global.auth.service.token.TokenProvider;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final OAuth2RegistrationComposite oauth2ProviderComposite;
    private final TokenProvider tokenProvider;

    public CheckNicknameDto.Response checkExistsNickname(CheckNicknameDto.Request request) {
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new BusinessException(AuthErrorCode.CONFLICT_EXIST_NICKNAME);
        } else {
            return CheckNicknameDto.Response.of(request.getNickname());
        }
    }

    @Transactional // TODO: 하는 일에 비해 트랜젝션 범위가 너무 큼
    public String authenticateOAuth(AuthenticateOAuthDto.Request request) {
        // deviceToekn으로 유저 유효성검사

        final OAuth2UserInfo userInfo = oauth2ProviderComposite.getOAuth2Strategy(request.getOAuthType())
            .getUserInfo(request.getValue());
        final User findUser = userRepository.findByEmail(userInfo.getEmail());
        if (findUser == null) {
            return tokenProvider.generateGuestToken(userInfo);
        } else {
            // 이메일로 다른 소셜 로그인으로 가입한 유저가 있는지 확인
            if (!Objects.equals(findUser.getOauth2Registration(), request.getOAuthType())) {
                throw new BusinessException(AuthErrorCode.CONFLICT_EXIST_EMAIL);
            }
            findUser.updateDeviceToken(request.getDeviceToken());
            findUser.updateOauthValue(userInfo.getOauthValue());
            return tokenProvider.generateUserToken(findUser);
        }
    }
}
