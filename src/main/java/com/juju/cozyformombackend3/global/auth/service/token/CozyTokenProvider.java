package com.juju.cozyformombackend3.global.auth.service.token;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.auth.model.OAuth2UserInfo;
import com.juju.cozyformombackend3.global.auth.model.UserRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CozyTokenProvider {

    private final static String PREFIX = "Bearer ";
    private final JWTTokenProperties jwtTokenProperties;

    public String generateUserToken(User user) {
        Date expirationDate = new Date(new Date().getTime() + jwtTokenProperties.getUserExpirationTime());
        Map<String, String> info = new HashMap<>();
        info.put("userId", String.valueOf(user.getId()));
        info.put("email", user.getEmail());
        info.put("role", UserRole.USER.name());
        return makeToken(expirationDate, user.getEmail(), info);
    }

    public String generateGuestToken(OAuth2UserInfo userInfo) {
        Date expirationDate = new Date(new Date().getTime() + jwtTokenProperties.getGuestExpirationTime());
        Map<String, String> info = new HashMap<>();
        info.put("email", userInfo.getEmail());
        info.put("role", UserRole.GUEST.name());
        info.put("oauthValue", userInfo.getOauthValue());
        return makeToken(expirationDate, userInfo.getEmail(), info);
    }

    private String makeToken(Date expiry, String email, Map<String, String> info) {
        Date now = new Date();

        return Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuer(jwtTokenProperties.getIssuer())
            .setIssuedAt(now)
            .setExpiration(expiry)
            .setSubject(email)
            .claim("info", info)
            .signWith(SignatureAlgorithm.HS256, jwtTokenProperties.getSecretKey())
            .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                // .verifyWith()
                // .build()
                // .parseSignedClaims(token)
                // .getPayload();
                .setSigningKey(jwtTokenProperties.getSecretKey()).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        log.info("claims: {}", claims);
        Set<SimpleGrantedAuthority> authoritySet = Set.of(new SimpleGrantedAuthority("ROLE_GUEST"));
        if (getInfo(token).get("role").equals(UserRole.USER.name())) {
            authoritySet = Set.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails
            .User(claims.getSubject(), "", authoritySet), token, authoritySet);
    }

    public Long getUserId(String token) {
        return Long.valueOf(getInfo(token).get("userId"));
    }

    public String getOAuthId(String token) {
        Claims claims = getClaims(token);
        return claims.get("oauthId", String.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(jwtTokenProperties.getSecretKey()).parseClaimsJws(token).getBody();
    }

    public long getExpiration(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().getTime();
    }

    public Map<String, String> getInfo(String token) {
        Claims claims = getClaims(token);
        return claims.get("info", Map.class);
    }
}
