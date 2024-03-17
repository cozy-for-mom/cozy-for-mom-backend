package com.juju.cozyformombackend3.domain.userlog.weight.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.userlog.weight.model.WeightRecord;

public interface WeightRepository extends JpaRepository<WeightRecord, Long>, CustomWeightRepository {

    Optional<WeightRecord> findByUserAndRecordAt(User user, LocalDate date);

    boolean existsByUserIdAndRecordAt(Long id, LocalDate date);

    WeightRecord findFirstByUserIdOrderByRecordAtDesc(Long userId);

    WeightRecord findByUserIdAndRecordAt(Long userId, LocalDate date);
}
