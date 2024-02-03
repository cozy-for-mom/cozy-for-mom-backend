package com.juju.cozyformombackend3.domain.babylog.baby.repository;

import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BabyProfileRepository extends JpaRepository<BabyProfile, Long>, CustomBabyProfileRepository {

	Optional<BabyProfile> findByBabyProfileId(Long babyProfileId);
}

