package com.juju.cozyformombackend3.domain.communitylog.cozylog.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.CreateCozyLogRequest;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.ModifyCozyLogRequest;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response.CozyLogDetailResponse;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.error.CozyLogErrorCode;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLog;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLogMode;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.repository.CozyLogRepository;
import com.juju.cozyformombackend3.domain.communitylog.scrap.repository.ScrapRepository;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CozyLogService {

	private final CozyLogRepository cozyLogRepository;
	private final ScrapRepository scrapRepository;

	@Transactional
	public Long saveCozyLog(User user, CreateCozyLogRequest request) {

		return cozyLogRepository.save(request.toEntity(user)).getId();
	}

	@Transactional
	public Long updateCozyLog(User user, ModifyCozyLogRequest request) {
		CozyLog foundCozyLog = findCozyLogById(request.getId());

		foundCozyLog.updateTextContent(request.getTitle(), request.getContent(), request.getMode());
		foundCozyLog.updateImageList(request.getImageList());

		return foundCozyLog.getId();
	}

	@Transactional
	public Long deleteCozyLog(Long userId, Long removeCozyLogId) {
		cozyLogRepository.deleteById(removeCozyLogId);

		return removeCozyLogId;
	}

	public CozyLogDetailResponse findCozyLogDetail(Long userId, Long cozyLogId) {
		CozyLog foundCozyLog = findCozyLogById(cozyLogId);
		checkAccessibleCozyLog(userId, foundCozyLog);
		Long scrapCount = scrapRepository.countByCozyLogId(cozyLogId);
		boolean isScraped = isScrapedCozyLog(userId, cozyLogId);

		return CozyLogDetailResponse.of(foundCozyLog, scrapCount, isScraped);

	}

	private void checkAccessibleCozyLog(Long userId, CozyLog foundCozyLog) {
		// 코지로그 공개 모드 확인
		if (CozyLogMode.PRIVATE == foundCozyLog.getMode()) {
			isMyCozyLog(userId, foundCozyLog.getUser());
		}
	}

	private void isMyCozyLog(Long userId, User user) {
		if (userId != user.getUserId()) {
			throw new BusinessException(CozyLogErrorCode.FORBIDDEN_INACCESSIBLE);
		}
	}

	private boolean isScrapedCozyLog(Long userId, Long cozyLogId) {
		return scrapRepository.existsByCozyLogIdAndUserUserId(cozyLogId, userId);
	}

	private CozyLog findCozyLogById(Long cozyLogId) {
		return cozyLogRepository.findById(cozyLogId)
			.orElseThrow(() -> new BusinessException(CozyLogErrorCode.NOT_FOUND_COZY_LOG));
	}
}
