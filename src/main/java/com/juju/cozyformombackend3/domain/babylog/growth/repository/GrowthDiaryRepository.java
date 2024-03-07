package com.juju.cozyformombackend3.domain.babylog.growth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthDiary;

public interface GrowthDiaryRepository extends JpaRepository<GrowthDiary, Long> {

}
