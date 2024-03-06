package com.juju.cozyformombackend3.domain.babylog.baby.dto.request;

import static com.juju.cozyformombackend3.global.util.DateParser.*;

import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.baby.model.Baby;
import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.babylog.baby.model.Gender;
import com.juju.cozyformombackend3.domain.user.model.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class CreateBabyProfileRequest {
	private String dueAt;
	private String profileImageUrl;
	private List<BabyDto> babies;

	@Getter
	@RequiredArgsConstructor
	private static class BabyDto {
		private final String name;
		private final String gender;

		public static BabyDto of(String name, String gender) {
			return new BabyDto(name, gender);
		}
	}

	public BabyProfile toBabyProfile(User user) {
		return BabyProfile.of(user, babies.size(),
			stringToLocalDate(dueAt),
			stringToLocalDate(dueAt),
			profileImageUrl);
	}

	public List<Baby> toBabyList(BabyProfile saveBabyProfile) {
		return babies.stream()
			.map(babyDto -> Baby.of(saveBabyProfile, babyDto.getName(), Gender.ofType(babyDto.getGender())))
			.toList();
	}
}
