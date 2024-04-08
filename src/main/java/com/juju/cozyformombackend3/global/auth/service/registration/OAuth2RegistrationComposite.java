package com.juju.cozyformombackend3.global.auth.service.registration;

import static java.util.function.Function.*;
import static java.util.stream.Collectors.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.juju.cozyformombackend3.global.auth.error.AuthErrorCode;
import com.juju.cozyformombackend3.global.auth.model.OAuth2Registration;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

@Component
public class OAuth2RegistrationComposite {
    private final Map<OAuth2Registration, OAuth2Strategy> oauth2RegistrationMap;

    public OAuth2RegistrationComposite(Set<OAuth2Strategy> clients) {
        this.oauth2RegistrationMap = clients.stream()
            .collect(toMap(OAuth2Strategy::getOAuth2Registration, identity()));
    }

    public OAuth2Strategy getOAuth2Strategy(OAuth2Registration registration) {
        return Optional.ofNullable(oauth2RegistrationMap.get(registration))
            .orElseThrow(() -> new BusinessException(AuthErrorCode.NOT_FOUND_NOT_MATCH_OAUTH2));
    }
}
