package com.juju.cozyformombackend3.domain.user.service;

import java.util.Map;
import java.util.Objects;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.user.dto.LogoutDto;
import com.juju.cozyformombackend3.domain.user.dto.SignUpDto;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.model.UserType;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.global.auth.error.AuthErrorCode;
import com.juju.cozyformombackend3.global.auth.service.token.CozyTokenProvider;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CozyTokenProvider cozyTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public SignUpDto.SignUpInfo registerUser(Map<String, String> guestInfo, SignUpDto.Request request) {
        // 해당 oauthId & Type으로 이미 회원가입했는지 확인
        if (userRepository.existsByOauthValueAndOauth2Registration(guestInfo.get("oauthValue"),
            request.getUserOAuthType())) {
            throw new BusinessException(AuthErrorCode.CONFLICT_EXIST_OAUTH_ACCOUNT);
        }
        if (userRepository.existsByEmail(request.getUserEmail())) {
            throw new BusinessException(AuthErrorCode.CONFLICT_EXIST_EMAIL);
        }

        // 없으면 사용자 등록하고
        final User saveUser = User.builder()
            .oauthValue(guestInfo.get("oauthValue"))
            .oauth2Registration(request.getUserInfo().getOAuthType())
            .userType(UserType.MOM)
            .name(request.getUserInfo().getName())
            .nickname(request.getUserInfo().getNickname())
            .birth(request.getUserInfo().getBirth())
            .email(request.getUserInfo().getEmail())
            .deviceToken(request.getUserInfo().getDeviceToken())
            .build();
        final User savedUser = userRepository.save(saveUser);
        if (Objects.nonNull(request.getBabyInfo())) {
            savedUser.addBabyProfile(request.toBabyProfile());
        }

        final String token = cozyTokenProvider.generateUserToken(savedUser);

        return SignUpDto.SignUpInfo.of(SignUpDto.Response.of(savedUser.getId()), token);
    }

    @Transactional
    public LogoutDto.Response logout(Long userId, String accessToken) {
        saveLogoutTokenInRedis(userId, accessToken);

        return LogoutDto.Response.of(userId);
    }

    private void saveLogoutTokenInRedis(Long userId, String accessToken) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String hashKey = "logout_token";
        String key = "logout_" + userId;
        log.info("token: {}", accessToken);

        // 만료 시간까지 남은 시간 계산
        // long expirationTime = cozyTokenProvider.getExpiration(accessToken);
        // long currentTime = Instant.now().getEpochSecond();
        // long ttl = expirationTime - currentTime;
        hashOperations.put(hashKey, key, accessToken);
        // redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }
}
