package com.juju.cozyformombackend3.domain.userlog.supplement.repository;

import com.juju.cozyformombackend3.domain.userlog.supplement.model.SupplementRecord;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplementRecordRepository
	extends JpaRepository<SupplementRecord, Long>, CustomSupplementRecordRepository {

}
