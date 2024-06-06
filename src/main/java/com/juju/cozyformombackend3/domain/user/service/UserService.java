package com.juju.cozyformombackend3.domain.user.service;

import java.util.Map;
import java.util.Objects;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.communitylog.comment.repository.CommentRepository;
import com.juju.cozyformombackend3.domain.user.controller.dto.Logout;
import com.juju.cozyformombackend3.domain.user.controller.dto.SignUp;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.model.UserType;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.global.auth.error.AuthErrorCode;
import com.juju.cozyformombackend3.global.auth.service.registration.OAuth2RegistrationComposite;
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
    private final CommentRepository commentRepository;
    private final OAuth2RegistrationComposite oauth2ProviderComposite;

    @Transactional
    public SignUp.SignUpInfo registerUser(Map<String, String> guestInfo, SignUp.Request request) {
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

        return SignUp.SignUpInfo.of(SignUp.Response.of(savedUser.getId()), token);
    }

    @Transactional
    public Logout.Response logout(Long userId, String accessToken) {
        saveLogoutTokenInRedis(userId, accessToken);

        return Logout.Response.of(userId);
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
        hashOperations.put(hashKey, accessToken, key);
        // redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }

    @Transactional
    public Logout.Response signOut(Long userId, String reason) {
        final User findUser = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(AuthErrorCode.NOT_FOUND_USER));

        // 로그아웃 토큰 저장
        // saveLogoutTokenInRedis(userId, accessToken);

        // 회원 탈퇴
        //comment isDelete
        commentRepository.updateCommentsIsDeletedByUserId(false, userId);
        // user delete
        userRepository.deleteById(userId);

        // oauth unlink
        oauth2ProviderComposite.getOAuth2Strategy(findUser.getOauth2Registration())
            .unlinkOAuth2Account(findUser.getOauthValue());
        return Logout.Response.of(userId);
    }
}
