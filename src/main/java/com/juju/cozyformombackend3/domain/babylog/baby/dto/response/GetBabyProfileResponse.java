package com.juju.cozyformombackend3.domain.babylog.baby.dto.response;

import java.util.List;

import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.babylog.baby.model.Gender;
import com.juju.cozyformombackend3.global.util.DateParser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetBabyProfileResponse {
	private final Long babyProfileId;
	private final Integer twins;
	private final String dueAt;
	private final String profileImageUrl;
	private final List<BabyDto> babies;

	public static GetBabyProfileResponse of(BabyProfile foundBabyProfile) {
		List<BabyDto> babies = foundBabyProfile.getBabyList().stream()
			.map(baby -> BabyDto.of(baby.getId(), baby.getName(), baby.getGender())).toList();

		return GetBabyProfileResponse.builder()
			.babyProfileId(foundBabyProfile.getId())
			.twins(foundBabyProfile.getTwins())
			.dueAt(DateParser.dateTimeToStringFormatDate(foundBabyProfile.getDueAt()))
			.profileImageUrl(foundBabyProfile.getImageUrl())
			.babies(babies)
			.build();
	}

	@Getter
	public static class BabyDto {
		private final Long babyId;
		private final String name;
		private final String gender;

		private BabyDto(final Long babyId, final String name, final Gender gender) {
			this.babyId = babyId;
			this.name = name;
			this.gender = gender.getType();
		}

		public static BabyDto of(final Long babyId, final String name, final Gender gender) {
			return new BabyDto(babyId, name, gender);
		}
	}
}

