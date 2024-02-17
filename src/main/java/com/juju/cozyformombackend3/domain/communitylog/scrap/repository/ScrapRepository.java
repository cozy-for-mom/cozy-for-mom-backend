package com.juju.cozyformombackend3.domain.communitylog.scrap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juju.cozyformombackend3.domain.communitylog.scrap.model.Scrap;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
	Long countByCozyLogId(Long cozyLogId);

	boolean existsByCozyLogIdAndUserUserId(Long cozyLogId, Long userId);
}
