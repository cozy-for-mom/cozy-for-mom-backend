package com.juju.cozyformombackend3.domain.growth.repository;

import com.juju.cozyformombackend3.domain.growth.model.GrowthRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrowthRecordRepository extends JpaRepository<GrowthRecord, Long> {

}
