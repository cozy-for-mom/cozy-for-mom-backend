package com.juju.cozyformombackend3.domain.user.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.user.dto.SignUpDto;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.model.UserType;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.global.auth.service.token.TokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public SignUpDto.SignUpInfo registerUser(SignUpDto.Request request) {
        // 해당 oauthId & Type으로 이미 회원가입했는지 확인

        // 없으면 사용자 등록하고
        final User saveUser = User.builder()
            .oauthId(null)
            .oauth2Registration(request.getUserInfo().getOAuthType())
            .userType(UserType.MOM)
            .name(request.getUserInfo().getName())
            .nickname(request.getUserInfo().getNickname())
            .birth(request.getUserInfo().getBirth())
            .email(request.getUserInfo().getEmail())
            .deviceToken(request.getUserInfo().getDeviceToken())
            .build();
        final User savedUser = userRepository.save(saveUser);
        if (Objects.nonNull(request.getBabyInfo())) {
            savedUser.addBabyProfile(request.toBabyProfile());
        }

        final String token = tokenProvider.generateUserToken(savedUser);

        return SignUpDto.SignUpInfo.of(SignUpDto.Response.of(savedUser.getId()), token);
    }
}
