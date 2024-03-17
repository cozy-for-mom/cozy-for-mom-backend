package com.juju.cozyformombackend3.global.auth.service;

import java.util.Objects;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.juju.cozyformombackend3.domain.user.model.OAuth2Registration;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.global.auth.model.OAuth2UserInfo;
import com.juju.cozyformombackend3.global.auth.service.registration.OAuth2RegistrationComposite;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final OAuth2RegistrationComposite oauth2ProviderComposite;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        final OAuth2User user = super.loadUser(userRequest);
        return getOAuth2User(userRequest, user);
    }

    private OAuth2User getOAuth2User(OAuth2UserRequest userRequest, OAuth2User user) {
        final OAuth2UserInfo userInfo = oauth2ProviderComposite.getOAuth2Strategy(getRegistration(userRequest))
            .getUserInfo(user);

        final User findUser = userRepository.findByEmail(userInfo.getEmail());
        if (Objects.isNull(findUser)) {
            // 권한이 guest 인 토큰을 발급하여 회원가입 하도록 함.
        }
        if (Objects.nonNull(findUser)) {
            // 권한  멤버, 유저의 id등의 정보를 담아서 토큰 발급
        }

        return null;
    }

    private OAuth2Registration getRegistration(final OAuth2UserRequest userRequest) {
        return OAuth2Registration.valueOf((userRequest.getClientRegistration().getRegistrationId()).toUpperCase());
    }
}
