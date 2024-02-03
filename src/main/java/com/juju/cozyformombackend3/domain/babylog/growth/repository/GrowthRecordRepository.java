package com.juju.cozyformombackend3.domain.babylog.growth.repository;

import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthRecord;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GrowthRecordRepository extends JpaRepository<GrowthRecord, Long> {

	List<GrowthRecord> findAllByRecordAt(LocalDate date);
}
