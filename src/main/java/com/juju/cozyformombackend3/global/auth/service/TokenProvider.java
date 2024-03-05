package com.juju.cozyformombackend3.global.auth.service;

import java.time.Duration;
import java.util.Date;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.juju.cozyformombackend3.domain.user.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TokenProvider {

	private final static String PREFIX = "Bearer ";

	private final static Integer EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7; // 7일

	private final UserDetailService userDetailService;

	//	@Value("${jwt.issuer}")
	private final String issuer = "juju";

	//	@Value("${jwt.secret}")
	private final String secretKey = "secret-juju-for-cozy-for-mom";

	public String generateToken(User user, Duration expiredAt) {
		Date now = new Date();
		return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
	}

	private String makeToken(Date expiry, User user) {
		Date now = new Date();
		return Jwts.builder()
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setIssuer(issuer)
			.setIssuedAt(now)
			.setExpiration(expiry)
			.setSubject(user.getEmail())
			.claim("userId", user.getId())
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	public boolean validToken(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Authentication getAuthentication(String token) {
		Claims claims = getClaims(token);
		Set<SimpleGrantedAuthority> authoritySet = Set.of(new SimpleGrantedAuthority("ROLE_USER"));
		return new UsernamePasswordAuthenticationToken(
			new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authoritySet), token,
			authoritySet);
	}

	public Long getUserId(String token) {
		Claims claims = getClaims(token);
		return claims.get("userId", Long.class);
	}

	private Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
	}
}
