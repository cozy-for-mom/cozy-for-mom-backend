package com.juju.cozyformombackend3.global.auth.filter;

import java.io.IOException;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.OncePerRequestFilter;

import com.juju.cozyformombackend3.global.auth.error.AuthErrorCode;
import com.juju.cozyformombackend3.global.error.exception.AuthException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CheckLogoutTokenFilter extends OncePerRequestFilter {

    private static final String LOGOUT_TOKEN_HASH_KEY = "logout_token";
    private final RedisTemplate<String, String> redisTemplate;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public CheckLogoutTokenFilter(RedisTemplate<String, String> redisTemplate,
        AuthenticationEntryPoint authenticationEntryPoint) {
        this.redisTemplate = redisTemplate;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        if (token != null && isTokenLoggedOut(token)) {
            AuthException authException = new AuthException(AuthErrorCode.UNAUTHORIZED);
            authenticationEntryPoint.commence(request, response, authException);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean isTokenLoggedOut(String token) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        return hashOperations.hasKey(LOGOUT_TOKEN_HASH_KEY, token);
    }
}
