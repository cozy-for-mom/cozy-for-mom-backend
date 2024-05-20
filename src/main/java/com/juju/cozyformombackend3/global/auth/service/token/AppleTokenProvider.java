package com.juju.cozyformombackend3.global.auth.service.token;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMException;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juju.cozyformombackend3.global.auth.error.AuthErrorCode;
import com.juju.cozyformombackend3.global.auth.service.registration.AppleAuthProperties;
import com.juju.cozyformombackend3.global.auth.service.registration.AppleOAuthPublicKeyDto;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class AppleTokenProvider {
    private final AppleAuthProperties appleAuthProperties;

    public String getEmail(AppleOAuthPublicKeyDto publicKeys, String identityToken) {
        Claims claims = getClaims(publicKeys, identityToken);
        return claims.get("email", String.class);
    }

    public Claims getClaims(AppleOAuthPublicKeyDto publicKeys, String identityToken) {

        try {
            String headerOfIdentityToken = identityToken.substring(0, identityToken.indexOf("."));
            Map<String, String> header = new ObjectMapper().readValue(
                new String(Base64.getDecoder().decode(headerOfIdentityToken), StandardCharsets.UTF_8), Map.class);
            AppleOAuthPublicKeyDto.Key key = publicKeys.getMatchedKeyBy(header.get("kid"), header.get("alg"))
                .orElseThrow(() -> new NullPointerException("Failed get public key from apple's id server."));

            byte[] nBytes = Base64.getUrlDecoder().decode(key.getN());
            byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());

            BigInteger n = new BigInteger(1, nBytes);
            BigInteger e = new BigInteger(1, eBytes);

            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance(key.getKty());
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(identityToken).getBody();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException");
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("InvalidKeySpecException");
        } catch (SignatureException e) {
            //토큰 서명 검증 or 구조 문제 (Invalid token)
            throw new RuntimeException("SignatureException");
        } catch (MalformedJwtException e) {
            //토큰 서명 검증 or 구조 문제 (Invalid token)
            throw new RuntimeException("MalformedJwtException");
        } catch (ExpiredJwtException e) {
            //토큰이 만료됐기 때문에 클라이언트는 토큰을 refresh 해야함.
            throw new RuntimeException("ExpiredJwtException");
        } catch (Exception e) {
            throw new RuntimeException("Exception in getClaims");
        }
    }

    public String generateClientSecret() {
        Date expirationDate = Date.from(LocalDateTime.now().plusMonths(5).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
            .setHeaderParam("kid", appleAuthProperties.getKeyId())
            .setHeaderParam("alg", "ES256")
            .setIssuer(appleAuthProperties.getTeamId())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(expirationDate)
            .setAudience(appleAuthProperties.getAud())
            .setSubject(appleAuthProperties.getClientId())
            .signWith(getPrivateKey(), SignatureAlgorithm.ES256)
            .compact();
    }

    public PrivateKey getPrivateKey() {
        log.info("getPrivateKey");
        ClassPathResource resource = new ClassPathResource("static/key/apple/AuthKey_5A63CQ878W.p8");
        log.info("resource: {}", resource.getDescription());
        String privateKey = null;
        try {
            privateKey = new String(Files.readAllBytes(Paths.get(resource.getURI())));
        } catch (IOException e) {
            throw new BusinessException(AuthErrorCode.NOT_FOUND_PRIVATE_KEY);
        }
        Reader pemReader = new StringReader(privateKey);
        PEMParser pemParser = new PEMParser(pemReader);
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        PrivateKeyInfo object = null;
        try {
            object = (PrivateKeyInfo)pemParser.readObject();
        } catch (IOException e) {
            throw new BusinessException(AuthErrorCode.SERVER_ERROR_CANT_READ_PRIVATE_KEY);
        }
        try {
            return converter.getPrivateKey(object);
        } catch (PEMException e) {
            throw new BusinessException(AuthErrorCode.SERVER_ERROR_CANT_CONVERT_PRIVATE_KEY);
        }
    }
}
