package com.juju.cozyformombackend3.global.auth.service;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.global.auth.dto.api.SignInDto;
import com.juju.cozyformombackend3.global.auth.error.AuthErrorCode;
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
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public String authenticate(SignInDto.Request request) {
        final User findUser = userRepository.findByOauthIdAndOauth2Registration(request.getOauthId(),
            request.getOAuthType()).orElseThrow(() -> new BusinessException(AuthErrorCode.NOT_FOUND_USER));
        // final UsernamePasswordAuthenticationToken authenticationToken =
        //     new UsernamePasswordAuthenticationToken(findUser.getEmail(), "",
        //         Set.of(new SimpleGrantedAuthority("ROLE_USER")));
        // log.info("before make authentication");
        // final Authentication authentication = authenticationManagerBuilder.getObject()
        //     .authenticate(authenticationToken);
        // log.info("before security contextholder");
        // SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("herhe before generateToken");

        return tokenProvider.generateUserToken(findUser);
    }
}
