package com.juju.cozyformombackend3.global.auth.service.registration;

import com.juju.cozyformombackend3.global.auth.error.AuthErrorCode;
import com.juju.cozyformombackend3.global.auth.model.OAuth2Registration;
import com.juju.cozyformombackend3.global.auth.model.OAuth2UserInfo;
import com.juju.cozyformombackend3.global.error.exception.AuthException;

public interface OAuth2Strategy {
    OAuth2Registration getOAuth2Registration();

    OAuth2UserInfo getUserInfo(String accessValue);

    boolean unlinkOAuth2Account(String oauthValue);

    default void isEmailExist(String email) {
        if (null == email) {
            throw new AuthException(AuthErrorCode.UNAUTHORIZED_OAUTH_RETURN_NULL_EMAIL);
        }
    }
}
