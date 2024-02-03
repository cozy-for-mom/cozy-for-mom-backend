package com.juju.cozyformombackend3.domain.babylog.growth.repository;

import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthDiary;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GrowthDiaryRepository extends JpaRepository<GrowthDiary, Long> {

	List<GrowthDiary> findAllByRecordAt(LocalDate date);

	Optional<GrowthDiary> findByBabyProfileAndRecordAt(BabyProfile findBabyProfile, LocalDate date);
}
