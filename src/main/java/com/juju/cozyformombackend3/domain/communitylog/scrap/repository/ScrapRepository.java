package com.juju.cozyformombackend3.domain.communitylog.scrap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juju.cozyformombackend3.domain.communitylog.scrap.model.Scrap;

public interface ScrapRepository extends JpaRepository<Scrap, Long>, CustomScrapRepository {
    Long countByCozyLogId(Long cozyLogId);

    boolean existsByCozyLogIdAndUserId(Long cozyLogId, Long userId);

    Optional<Scrap> findByCozyLogIdAndUserId(Long cozyLogId, Long userId);

    Long countByUserId(Long userId);

    void deleteAllByCozyLogId(Long removeCozyLogId);
}
