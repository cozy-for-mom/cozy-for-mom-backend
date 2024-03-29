package com.juju.cozyformombackend3.domain.communitylog.scrap.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.CozyLogSummary;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.error.CozyLogErrorCode;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.repository.CozyLogRepository;
import com.juju.cozyformombackend3.domain.communitylog.scrap.dto.request.ApplyScrapRequest;
import com.juju.cozyformombackend3.domain.communitylog.scrap.dto.request.UnscrapListRequest;
import com.juju.cozyformombackend3.domain.communitylog.scrap.dto.response.FindScrapListResponse;
import com.juju.cozyformombackend3.domain.communitylog.scrap.dto.response.ScrapResponse;
import com.juju.cozyformombackend3.domain.communitylog.scrap.error.ScrapErrorCode;
import com.juju.cozyformombackend3.domain.communitylog.scrap.model.Scrap;
import com.juju.cozyformombackend3.domain.communitylog.scrap.repository.ScrapRepository;
import com.juju.cozyformombackend3.domain.user.error.UserErrorCode;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapRepository scrapRepository;
    private final CozyLogRepository cozyLogRepository;
    private final UserRepository userRepository;

    @Transactional
    public ScrapResponse saveScrap(Long userId, ApplyScrapRequest request) {
        if (request.getIsScraped()) {
            return addScrap(userId, request);
        } else {
            return deleteScrap(userId, request);
        }
    }

    private ScrapResponse deleteScrap(Long userId, ApplyScrapRequest request) {
        Scrap foundScrap = scrapRepository.findByCozyLogIdAndUserId(request.getCozyLogId(), userId)
            .orElseThrow(() -> new BusinessException(ScrapErrorCode.NOT_FOUND_NOT_EXIST));
        scrapRepository.delete(foundScrap);
        return ScrapResponse.of(request.getCozyLogId());
    }

    private ScrapResponse addScrap(Long userId, ApplyScrapRequest request) {
        notExistsCozyLog(request.getCozyLogId());
        existsScrap(userId, request.getCozyLogId());
        User foundUser = findUserById(userId);
        scrapRepository.save(request.toScrap(foundUser));
        return ScrapResponse.of(request.getCozyLogId());
    }

    private void existsScrap(Long userId, Long cozyLogId) {
        if (scrapRepository.existsByCozyLogIdAndUserId(cozyLogId, userId)) {
            throw new BusinessException(ScrapErrorCode.CONFLICT_ALREADY_EXIST);
        }
    }

    private void notExistsCozyLog(Long cozyLogId) {
        if (!cozyLogRepository.existsById(cozyLogId)) {
            throw new BusinessException(CozyLogErrorCode.NOT_FOUND_COZY_LOG);
        }
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));
    }

    @Transactional
    public void deleteScrapList(Long userId, UnscrapListRequest request) {
        scrapRepository.deleteScrapByUserIdAndCozyLogIds(userId, request.getCozyLogIds());
    }

    public FindScrapListResponse findScrapList(Long userId, Long reportId, Long size) {
        List<CozyLogSummary> cozyLogs = scrapRepository.findScrapListById(userId, reportId, size);
        Long count = scrapRepository.countByUserId(userId);

        return FindScrapListResponse.of(count, cozyLogs);
    }
}
