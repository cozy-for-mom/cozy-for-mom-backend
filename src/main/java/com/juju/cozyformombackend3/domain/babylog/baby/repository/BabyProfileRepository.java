package com.juju.cozyformombackend3.domain.babylog.baby.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;

public interface BabyProfileRepository extends JpaRepository<BabyProfile, Long>, CustomBabyProfileRepository {

	Optional<BabyProfile> findById(Long babyProfileId);
}

