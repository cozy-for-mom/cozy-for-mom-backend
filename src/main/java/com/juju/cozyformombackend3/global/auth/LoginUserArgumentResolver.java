package com.juju.cozyformombackend3.global.auth;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.juju.cozyformombackend3.global.auth.annotation.LoginUserId;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean isLoginUserIdAnnotation = parameter.getParameterAnnotation(LoginUserId.class) != null;
		boolean isUserClass = Long.class.equals(parameter.getParameterType());

		return isLoginUserIdAnnotation && isUserClass;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		return 1L;
	}
}
