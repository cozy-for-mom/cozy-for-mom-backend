package com.juju.cozyformombackend3.domain.userlog.bloodsugar.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juju.cozyformombackend3.domain.userlog.bloodsugar.model.BloodSugarRecord;
import com.juju.cozyformombackend3.domain.userlog.bloodsugar.model.BloodSugarRecordType;

public interface BloodSugarRepository extends JpaRepository<BloodSugarRecord, Long>, CustomBloodSugarRepository {

    boolean existsByUserIdAndRecordAtAndBloodSugarRecordType(Long id, LocalDate date, BloodSugarRecordType type);
}
