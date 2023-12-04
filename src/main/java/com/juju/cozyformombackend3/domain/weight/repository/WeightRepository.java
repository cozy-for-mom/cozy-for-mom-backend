package com.juju.cozyformombackend3.domain.weight.repository;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.weight.model.WeightRecord;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightRepository extends JpaRepository<WeightRecord, Long> {
	
	boolean existsByUserAndRecordDate(User user, LocalDate date);
}
