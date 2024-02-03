package com.juju.cozyformombackend3.domain.babylog.growth.service;

import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.babylog.baby.repository.BabyProfileRepository;
import com.juju.cozyformombackend3.domain.babylog.baby.repository.BabyRepository;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.object.FindGrowthDiaryRecord;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.request.SaveGrowthRequest;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.request.UpdateGrowthRequest;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.response.FindGrowthResponse;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.response.SaveGrowthResponse;
import com.juju.cozyformombackend3.domain.babylog.growth.dto.response.UpdateGrowthResponse;
import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthDiary;
import com.juju.cozyformombackend3.domain.babylog.growth.model.GrowthRecord;
import com.juju.cozyformombackend3.domain.babylog.growth.repository.GrowthDiaryRepository;
import com.juju.cozyformombackend3.domain.babylog.growth.repository.GrowthRecordRepository;
import com.juju.cozyformombackend3.domain.user.model.User;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GrowthService {

	private final GrowthDiaryRepository growthDiaryRepository;

	private final GrowthRecordRepository growthRecordRepository;

	private final BabyProfileRepository babyProfileRepository;

	private final BabyRepository babyRepository;

	@Transactional
	public SaveGrowthResponse saveGrowth(User user, SaveGrowthRequest request) {
		BabyProfile foundBabyProfile = findBabyProfileById(request.getBabyProfileId());
		isUserAuthorized(foundBabyProfile.getUser(), user);

		GrowthDiary savedGrowthDiary = growthDiaryRepository.save(request.toGrowthDiary(foundBabyProfile));

		List<GrowthRecord> saveGrowthRecordList = foundBabyProfile.getBabyList().stream()
			.map(baby -> request.toGrowthRecord(baby))
			.collect(Collectors.toList());
		growthRecordRepository.saveAll(saveGrowthRecordList);

		return SaveGrowthResponse.of(savedGrowthDiary.getGrowthDiaryId(),
			saveGrowthRecordList.stream()
				.map(GrowthRecord::getGrowthRecordId)
				.collect(Collectors.toList()));
	}

	@Transactional
	public UpdateGrowthResponse updateGrowth(User user, UpdateGrowthRequest request) {
		BabyProfile foundBabyProfile = findBabyProfileById(request.getBabyProfileId());
		isUserAuthorized(foundBabyProfile.getUser(), user);

		GrowthDiary foundGrowthDairy = growthDiaryRepository.findById(request.getGrowthDiaryId())
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 성장일기입니다."));
		foundGrowthDairy.update(request.getGrowthDiaryDto());

		List<Long> foundGrowthRecordList = request.getBabies().stream()
			.map(baby -> {
				GrowthRecord foundGrowthRecord = growthRecordRepository.findById(baby.getGrowthRecordId())
					.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 record입니다."));
				foundGrowthRecord.update(baby);
				return foundGrowthRecord.getGrowthRecordId();
			}).collect(Collectors.toList());

		return UpdateGrowthResponse.of(foundGrowthDairy.getGrowthDiaryId(), foundGrowthRecordList);
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

	public void deleteGrowth(User user, LocalDate date) {
		List<GrowthDiary> growthDiaryList = growthDiaryRepository.findAllByRecordAt(date);
		growthDiaryList.forEach(growthDiary -> {
			isUserAuthorized(growthDiary.getBabyProfile().getUser(), user);
			growthDiaryRepository.delete(growthDiary);
		});

		List<GrowthRecord> growthRecordList = growthRecordRepository.findAllByRecordAt(date);
		growthRecordList.forEach(growthRecord -> {
			isUserAuthorized(growthRecord.getBaby().getBabyProfile().getUser(), user);
			growthRecordRepository.delete(growthRecord);
		});
	}

	public FindGrowthResponse getGrowth(Long userId, Long babyProfileId, LocalDate date) {
		//        FindGrowthDiaryRecord findGrowthDiaryRecord = babyProfileRepository.searchGrowthRecord(userId, date);
		BabyProfile findBabyProfile = babyProfileRepository.findByBabyProfileId(babyProfileId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이입니다."));
		GrowthDiary findGrowthDiary = growthDiaryRepository.findByBabyProfileAndRecordAt(findBabyProfile, date)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 성장일기입니다."));
		FindGrowthDiaryRecord findGrowthDiaryRecord = new FindGrowthDiaryRecord(findBabyProfile.getBabyProfileId(),
			findGrowthDiary, findBabyProfile.getBabyList());

		return FindGrowthResponse.of(findGrowthDiaryRecord);
	}
}
