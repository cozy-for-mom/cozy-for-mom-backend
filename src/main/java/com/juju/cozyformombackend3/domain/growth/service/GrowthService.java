package com.juju.cozyformombackend3.domain.growth.service;

import com.juju.cozyformombackend3.domain.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.baby.repository.BabyProfileRepository;
import com.juju.cozyformombackend3.domain.baby.repository.BabyRepository;
import com.juju.cozyformombackend3.domain.growth.dto.request.SaveGrowthRequest;
import com.juju.cozyformombackend3.domain.growth.dto.response.SaveGrowthResponse;
import com.juju.cozyformombackend3.domain.growth.model.GrowthDiary;
import com.juju.cozyformombackend3.domain.growth.model.GrowthRecord;
import com.juju.cozyformombackend3.domain.growth.repository.GrowthDiaryRepository;
import com.juju.cozyformombackend3.domain.growth.repository.GrowthRecordRepository;
import com.juju.cozyformombackend3.domain.user.model.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrowthService {

	private final GrowthDiaryRepository growthDiaryRepository;

	private final GrowthRecordRepository growthRecordRepository;

	private final BabyProfileRepository babyProfileRepository;

	private final BabyRepository babyRepository;

	public SaveGrowthResponse saveGrowth(User user, SaveGrowthRequest request) {
		BabyProfile foundBabyProfile = babyProfileRepository.findById(request.getBabyProfileId())
						.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이입니다."));

		if (foundBabyProfile.getUser() != user) {
			throw new IllegalArgumentException("권한이 없습니다.");
		}
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
}
