package com.juju.cozyformombackend3.domain.communitylog.cozylog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.CreateCozyLogRequest;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.ModifyCozyLogRequest;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLog;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.repository.CozyLogRepository;
import com.juju.cozyformombackend3.domain.user.model.User;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CozyLogService {

	private final CozyLogRepository cozyLogRepository;

	@Transactional
	public Long saveCozyLog(User user, CreateCozyLogRequest request) {

		return cozyLogRepository.save(request.toEntity(user)).getId();
	}

	@Transactional
	public Long updateCozyLog(User user, ModifyCozyLogRequest request) {
		CozyLog foundCozyLog = cozyLogRepository.findById(request.getId())
			.orElseThrow(() -> new IllegalArgumentException("해당 cozy log가 존재하지 않습니다."));

		foundCozyLog.updateTextContent(request.getTitle(), request.getContent(), request.getMode());
		foundCozyLog.updateImageList(request.getImageList());

		return foundCozyLog.getId();
	}
}
