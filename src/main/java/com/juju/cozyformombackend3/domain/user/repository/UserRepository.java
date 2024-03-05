package com.juju.cozyformombackend3.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juju.cozyformombackend3.domain.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {

	Optional<User> findByEmail(String email);
}
