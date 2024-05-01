package com.juju.cozyformombackend3.domain.babylog.growth.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.babylog.baby.error.BabyErrorCode;
import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.babylog.baby.repository.BabyProfileRepository;
import com.juju.cozyformombackend3.domain.babylog.growth.controller.dto.FindGrowth;
import com.juju.cozyformombackend3.domain.babylog.growth.controller.dto.FindGrowthList;
import com.juju.cozyformombackend3.domain.babylog.growth.controller.dto.SaveGrowth;
import com.juju.cozyformombackend3.domain.babylog.growth.controller.dto.UpdateGrowth;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.object.GrowthSummary;
import com.juju.cozyformombackend3.domain.babylog.growth.error.GrowthErrorCode;
import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthDiary;
import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthRecord;
import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthReport;
import com.juju.cozyformombackend3.domain.babylog.growth.repository.GrowthDiaryRepository;
import com.juju.cozyformombackend3.domain.babylog.growth.repository.GrowthRecordRepository;
import com.juju.cozyformombackend3.domain.babylog.growth.repository.GrowthReportRepository;
import com.juju.cozyformombackend3.domain.user.error.UserErrorCode;
import com.juju.cozyformombackend3.domain.user.model.User;
import com.juju.cozyformombackend3.domain.user.repository.UserRepository;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GrowthService {

    private final UserRepository userRepository;
    private final GrowthReportRepository growthReportRepository;
    private final GrowthDiaryRepository growthDiaryRepository;
    private final GrowthRecordRepository growthRecordRepository;
    private final BabyProfileRepository babyProfileRepository;

    @Transactional
    public SaveGrowth.Response saveGrowth(Long userId, SaveGrowth.Request request) {
        User user = findUserById(userId);
        BabyProfile foundBabyProfile = findBabyProfileById(request.getBabyProfileId());
        isUserAuthorized(foundBabyProfile.getUser(), user);

        GrowthDiary savedDiary = growthDiaryRepository.save(request.toGrowthDiary());
        GrowthReport saveReport = GrowthReport.builder()
            .babyProfile(foundBabyProfile)
            .growthDiary(savedDiary)
            .recordAt(request.getRecordAt())
            .build();
        GrowthReport savedReport = growthReportRepository.save(saveReport);

        request.toGrowthRecordList(foundBabyProfile.getBabyList())
            .forEach(growthRecord -> {
                growthRecordRepository.save(growthRecord);
                saveReport.updateGrowthRecord(growthRecord);
            });

        return SaveGrowth.Response.of(savedReport);
    }

    @Transactional
    public UpdateGrowth.Response updateGrowth(Long userId, Long reportId, UpdateGrowth.Request request) {
        User user = findUserById(userId);
        GrowthReport findReport = findGrowthReportById(reportId);
        // isUserAuthorized(findReport.getGrowthDiary().getBabyProfile().getUser(), user);

        findReport.getGrowthDiary().update(request.getGrowthDiaryDto());
        request.getBabies().forEach(babyDto -> {
            GrowthRecord foundGrowthRecord = findReport.getGrowthRecordList()
                .stream()
                .filter(dto -> dto.getId().equals(babyDto.getGrowthRecordId()))
                .findFirst()
                .orElseThrow(() -> new BusinessException(GrowthErrorCode.NOT_FOUND_MATCH_BABY_GROWTH_RECORD));

            foundGrowthRecord.update(babyDto);
        });
        return UpdateGrowth.Response.of(findReport);
    }

    private BabyProfile findBabyProfileById(Long babyProfileId) {
        return babyProfileRepository.findById(babyProfileId)
            .orElseThrow(() -> new BusinessException(BabyErrorCode.NOT_FOUND_BABY_PROFILE));
    }

    private void isUserAuthorized(User mom, User target) {
        if (mom != target) {
            throw new BusinessException(UserErrorCode.FORBIDDEN_NOT_YOUR_RESOURCE);
        }
    }

    @Transactional
    public void deleteGrowth(Long userId, Long reportId) {
        GrowthReport findReport = findGrowthReportById(reportId);
        // growthDiaryRepository.delete(findReport.getGrowthDiary());
        growthReportRepository.delete(findReport);
    }

    public FindGrowth.Response getGrowth(Long userId, Long reportId) {
        GrowthReport findReport = findGrowthReportById(reportId);

        return FindGrowth.Response.of(findReport);
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));
    }

    private GrowthReport findGrowthReportById(Long reportId) {
        return growthReportRepository.findById(reportId)
            .orElseThrow(() -> new BusinessException(GrowthErrorCode.NOT_FOUND_GROWTH_REPORT));
    }

    public FindGrowthList.Response getGrowthList(Long babyProfileId, Long reportId, Long size) {
        List<GrowthSummary> growthSummaryList = growthReportRepository
            .findGrowthSummaryListByBabyProfileIdAndLastIdAndSize(babyProfileId, reportId, size);

        // TODO
        LocalDate nextExaminationDate = LocalDate.now().plusWeeks(1);

        return FindGrowthList.Response.of(nextExaminationDate.toString(), growthSummaryList);
    }
}
