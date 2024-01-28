package com.juju.cozyformombackend3.domain.supplement.repository;

import com.juju.cozyformombackend3.domain.supplement.model.SupplementRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplementRecordRepository extends JpaRepository<SupplementRecord, Long>, CustomSupplementRecordRepository {

}
