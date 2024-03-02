package com.juju.cozyformombackend3.domain.babylog.growth.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.babylog.baby.repository.BabyProfileRepository;
import com.juju.cozyformombackend3.domain.babylog.baby.repository.BabyRepository;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.object.GrowthSummary;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.request.SaveGrowthRequest;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.request.UpdateGrowthRequest;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.response.FindGrowthListResponse;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.response.FindGrowthResponse;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.response.SaveGrowthResponse;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.response.UpdateGrowthResponse;
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
	private final BabyRepository babyRepository;

	@Transactional
	public SaveGrowthResponse saveGrowth(Long userId, SaveGrowthRequest request) {
		User user = findUserById(userId);
		BabyProfile foundBabyProfile = findBabyProfileById(request.getBabyProfileId());
		isUserAuthorized(foundBabyProfile.getUser(), user);

		GrowthDiary savedDiary = growthDiaryRepository.save(request.toGrowthDiary(foundBabyProfile));
		GrowthReport saveReport = GrowthReport.builder().growthDiary(savedDiary)
			.recordDate(request.getDate())
			.build();
		GrowthReport savedReport = growthReportRepository.save(saveReport);
		foundBabyProfile.getBabyList().stream()
			.map(baby -> request.toGrowthRecord(baby))
			.forEach(growthRecord -> {
				growthRecordRepository.save(growthRecord);
				saveReport.updateGrowthRecord(growthRecord);
			});

		return SaveGrowthResponse.of(savedReport);
	}

	@Transactional
	public UpdateGrowthResponse updateGrowth(Long userId, Long reportId, UpdateGrowthRequest request) {
		User user = findUserById(userId);
		GrowthReport findReport = findGrowthReportById(reportId);
		isUserAuthorized(findReport.getGrowthDiary().getBabyProfile().getUser(), user);

		findReport.getGrowthDiary().update(request.getGrowthDiaryDto());
		request.getBabies().stream()
			.forEach(baby -> {
				GrowthRecord foundGrowthRecord = growthRecordRepository.findById(baby.getGrowthRecordId())
					.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 record입니다."));
				foundGrowthRecord.update(baby);
			});
		return UpdateGrowthResponse.of(findReport);
	}

	private BabyProfile findBabyProfileById(Long babyProfileId) {
		return babyProfileRepository.findById(babyProfileId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이입니다."));
	}

	private void isUserAuthorized(User mom, User target) {
		if (mom != target) {
			throw new IllegalArgumentException("권한이 없습니다.");
		}
	}

	@Transactional
	public void deleteGrowth(Long userId, Long reportId) {
		GrowthReport findReport = findGrowthReportById(reportId);
		growthDiaryRepository.delete(findReport.getGrowthDiary());
		growthReportRepository.delete(findReport);
	}

	public FindGrowthResponse getGrowth(Long userId, Long reportId) {
		GrowthReport findReport = findGrowthReportById(reportId);

		return FindGrowthResponse.of(findReport);
	}

	private User findUserById(Long userId) {
		return userRepository.findByUserId(userId)
			.orElseThrow(() -> new BusinessException(UserErrorCode.NOT_FOUND_USER));
	}

	private GrowthReport findGrowthReportById(Long reportId) {
		return growthReportRepository.findById(reportId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 성장 보고서 아이디입니다."));
	}

	public FindGrowthListResponse getGrowthList(Long reportId, Long size) {
		List<GrowthSummary> growthSummaryList = growthReportRepository
			.findGrowthSummaryListByLastIdAndSize(reportId, size);

		// TODO
		LocalDate nextExaminationDate = LocalDate.now().plusWeeks(1);

		return FindGrowthListResponse.of(nextExaminationDate.toString(), growthSummaryList);
	}
}
