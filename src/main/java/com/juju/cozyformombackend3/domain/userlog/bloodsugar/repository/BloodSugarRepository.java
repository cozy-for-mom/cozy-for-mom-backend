package com.juju.cozyformombackend3.domain.userlog.bloodsugar.repository;

import com.juju.cozyformombackend3.domain.userlog.bloodsugar.model.BloodSugarRecord;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodSugarRepository extends JpaRepository<BloodSugarRecord, Long>, CustomBloodSugarRepository {

}
