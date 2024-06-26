package com.juju.cozyformombackend3.domain.communitylog.cozylog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLog;

public interface CozyLogRepository extends JpaRepository<CozyLog, Long>, CustomCozyLogRepository {
    Long countByUserId(Long userId);

    List<CozyLog> findAllByUserId(Long userId);

}
