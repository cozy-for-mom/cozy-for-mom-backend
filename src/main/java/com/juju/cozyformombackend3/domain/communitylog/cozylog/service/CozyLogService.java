package com.juju.cozyformombackend3.domain.communitylog.cozylog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.CreateCozyLogRequest;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.repository.CozyLogRepository;
import com.juju.cozyformombackend3.domain.user.model.User;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CozyLogService {

	private final CozyLogRepository cozyLogRepository;

	@Transactional
	public Long createCozyLog(User user, CreateCozyLogRequest request) {

		return cozyLogRepository.save(request.toEntity(user)).getId();
	}
}
