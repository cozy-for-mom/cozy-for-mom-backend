package com.juju.cozyformombackend3.domain.communitylog.cozylog.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.communitylog.cozylog.controller.CozyLogCondition;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.RecentSearchKeyword;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.SearchKeywordRedis;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.querydto.CozyLogSummary;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.CreateCozyLogRequest;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.DeleteMyCozyLogListRequest;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request.ModifyCozyLogRequest;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response.CozyLogDetailResponse;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response.FindMyCozyLogListResponse;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response.GetCozyLogListResponse;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.response.SearchCozyLogResponse;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.error.CozyLogErrorCode;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLog;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.model.CozyLogMode;
import com.juju.cozyformombackend3.domain.communitylog.cozylog.repository.CozyLogRepository;
import com.juju.cozyformombackend3.domain.communitylog.scrap.repository.ScrapRepository;
import com.juju.cozyformombackend3.domain.user.error.UserErrorCode;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class CozyLogService {

    private final CozyLogRepository cozyLogRepository;
    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, SearchKeywordRedis> redisTemplate;

    @Transactional
    public Long saveCozyLog(Long userId, CreateCozyLogRequest request) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));
        return cozyLogRepository.save(request.toEntity(user)).getId();
    }

    @Transactional
    public Long updateCozyLog(Long userId, ModifyCozyLogRequest request) {
        CozyLog foundCozyLog = findCozyLogById(request.getId());

        foundCozyLog.updateTextContent(request.getTitle(), request.getContent(), request.getMode());
        foundCozyLog.updateImageList(request.getImageList());

        return foundCozyLog.getId();
    }

    @Transactional
    public Long deleteCozyLog(Long userId, Long removeCozyLogId) {
        cozyLogRepository.deleteById(removeCozyLogId);
        scrapRepository.deleteAllByCozyLogId(removeCozyLogId);

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
        if (userId != user.getId()) {
            throw new BusinessException(CozyLogErrorCode.FORBIDDEN_INACCESSIBLE);
        }
    }

    private boolean isScrapedCozyLog(Long userId, Long cozyLogId) {
        return scrapRepository.existsByCozyLogIdAndUserId(cozyLogId, userId);
    }

    private CozyLog findCozyLogById(Long cozyLogId) {
        return cozyLogRepository.findById(cozyLogId)
            .orElseThrow(() -> new BusinessException(CozyLogErrorCode.NOT_FOUND_COZY_LOG));
    }

    public GetCozyLogListResponse findCozyLogList(final CozyLogCondition condition) {
        List<CozyLogSummary> cozyLogs = cozyLogRepository.searchCozyLogListByCondition(condition);

        return GetCozyLogListResponse.of(cozyLogs);
    }

    public FindMyCozyLogListResponse findMyCozyLog(final CozyLogCondition condition) {
        List<CozyLogSummary> cozyLogs = cozyLogRepository.searchCozyLogListByCondition(condition);

        Long count = cozyLogRepository.countByUserId(condition.getWriterId());
        return FindMyCozyLogListResponse.of(count, cozyLogs);
    }

    @Transactional
    public void deleteCozyLogList(Long userId, DeleteMyCozyLogListRequest request) {
        cozyLogRepository.deleteCozyLogByUserIdAndCozyLogIds(userId, request.getCozyLogIds());
    }

    public SearchCozyLogResponse searchCozyLog(Long userId, final CozyLogCondition condition) {
        List<CozyLogSummary> cozyLogs = cozyLogRepository.searchCozyLogListByCondition(condition);
        Long totalCount = cozyLogRepository.countByCondition(condition);
        saveRecentSearchLog(userId, condition.getKeyword());
        return SearchCozyLogResponse.of(totalCount, cozyLogs);
    }

    @Transactional
    public void deleteAllCozyLog(Long userId) {
        List<CozyLog> findMyCozyLog = cozyLogRepository.findAllByUserId(userId);
        log.info(findMyCozyLog.toString());
        cozyLogRepository.deleteCozyLogByUserIdAndCozyLogIds(userId,
            findMyCozyLog.stream().map(CozyLog::getId).toList());
    }

    public RecentSearchKeyword.Response findRecentSearchKeyword(Long userId) {
        String key = "SearchKeyword_" + userId;
        List<SearchKeywordRedis> keywordList = redisTemplate.opsForList()
            .range(key, 0, 10L);

        return Objects.nonNull(keywordList)
            ? RecentSearchKeyword.Response.of(keywordList.stream().map(SearchKeywordRedis::getKeyword).toList())
            : null;
    }

    private void saveRecentSearchLog(Long userId, String keyword) {
        String now = LocalDateTime.now().toString();

        String key = "SearchKeyword_" + userId;
        SearchKeywordRedis value = SearchKeywordRedis.builder()
            .keyword(keyword)
            .createdAt(now)
            .build();

        Long size = redisTemplate.opsForList().size(key);
        if (10L == size) {
            redisTemplate.opsForList().rightPop(key);
        }

        redisTemplate.opsForList().leftPush(key, value);
    }
}
