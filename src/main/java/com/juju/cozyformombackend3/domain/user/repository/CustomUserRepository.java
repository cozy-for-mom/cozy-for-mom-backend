package com.juju.cozyformombackend3.domain.user.repository;

import com.juju.cozyformombackend3.domain.user.dto.object.UserSummary;

public interface CustomUserRepository {
	UserSummary findUserSummaryById(Long userId);
}
