package com.juju.cozyformombackend3.global.config.web;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;
import com.juju.cozyformombackend3.global.auth.error.AuthErrorCode;
import com.juju.cozyformombackend3.global.auth.service.token.CozyTokenProvider;
import com.juju.cozyformombackend3.global.error.exception.AuthException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final CozyTokenProvider cozyTokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserIdAnnotation = parameter.getParameterAnnotation(LoginUserId.class) != null;
        boolean isUserClass = Long.class.equals(parameter.getParameterType());

        return isLoginUserIdAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        log.info("JwtAuthorizationArgumentResolver 동작!!");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() != null) {
            String token = authentication.getCredentials().toString();
            return cozyTokenProvider.getUserId(token);
        }
        // return null;

        // 토큰 값이 없으면 에러
        throw new AuthException(AuthErrorCode.UNAUTHORIZED);
        // }
        // return 1L;
    }
}
