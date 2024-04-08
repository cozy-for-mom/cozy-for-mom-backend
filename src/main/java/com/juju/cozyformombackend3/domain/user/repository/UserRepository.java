package com.juju.cozyformombackend3.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.auth.model.OAuth2Registration;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {

    User findByEmail(String email);

    Optional<User> findByOauthIdAndOauth2Registration(String oauthId, OAuth2Registration oAuthType);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    boolean existsByOauthIdAndOauth2Registration(String oauthId, OAuth2Registration oauthType);
}
