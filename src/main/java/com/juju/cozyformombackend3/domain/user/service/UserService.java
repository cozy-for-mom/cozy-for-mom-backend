package com.juju.cozyformombackend3.domain.user.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.user.dto.SignUpDto;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.model.UserType;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public SignUpDto.Response registerUser(SignUpDto.Request request) {
        // 해당 oauthId & Type으로 이미 회원가입했는지 확인

        // 없으면 사용자 등록하고
        final User saveUser = User.builder()
            .oauthId(request.getUserInfo().getOauthId())
            .oauth2Registration(request.getUserInfo().getOAuthType())
            .userType(UserType.MOM)
            .name(request.getUserInfo().getName())
            .nickname(request.getUserInfo().getNickname())
            .birth(request.getUserInfo().getBirth())
            .email(request.getUserInfo().getEmail())
            .build();
        final User savedUser = userRepository.save(saveUser);
        if (Objects.nonNull(request.getBabyInfo())) {
            savedUser.addBabyProfile(request.toBabyProfile());
        }

        return SignUpDto.Response.of(savedUser.getId());
    }
}
