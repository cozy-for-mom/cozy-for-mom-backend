package com.juju.cozyformombackend3.domain.userlog.supplement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.userlog.supplement.model.Supplement;

public interface SupplementRepository extends JpaRepository<Supplement, Long> {

	boolean existsByName(String supplementName);

	Optional<Supplement> findByNameAndUser(String supplementName, User user);
}
