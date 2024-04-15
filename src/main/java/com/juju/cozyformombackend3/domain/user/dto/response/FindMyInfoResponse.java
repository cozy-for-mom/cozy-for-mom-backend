package com.juju.cozyformombackend3.domain.user.dto.response;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.juju.cozyformombackend3.domain.babylog.baby.model.BabyProfile;
import com.juju.cozyformombackend3.domain.user.dto.object.UserSummary;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FindMyInfoResponse {
    private final String name;
    private final String nickname;
    private final String introduce;
    private final String imageUrl;
    private final String birth;
    private final String email;
    private List<BabyProfileDto> babyProfile = new ArrayList<>();
    private BabyProfileDto recentBabyProfile;
    private Long dDay;

    public Long getdDay() {
        return this.dDay;
    }

    public static FindMyInfoResponse of(UserSummary summary) {
        FindMyInfoResponse response = new FindMyInfoResponse(summary.getName(), summary.getNickname(),
            summary.getIntroduce(), summary.getImageUrl(), summary.getBirth(), summary.getEmail());

        response.babyProfile = summary.getBabyProfile().stream()
            .map(babyProfile -> {
                BabyProfileDto dto = BabyProfileDto.of(babyProfile);
                if (Objects.equals(babyProfile.getId(), summary.getRecentBabyProfileId())) {
                    response.recentBabyProfile = dto;
                    response.dDay = Duration.between(LocalDateTime.now(), babyProfile.getDueAt().atStartOfDay())
                        .toDays();
                }
                return dto;
            })
            .toList();

        return response;
    }

    @Getter
    private static class BabyProfileDto {
        private final Long babyProfileId;
        private final String babyProfileImageUrl;
        private final List<BabyDto> babies = new ArrayList<>();

        private BabyProfileDto(Long babyProfileId, String babyProfileImageUrl) {

            this.babyProfileId = babyProfileId;
            this.babyProfileImageUrl = babyProfileImageUrl;
        }

        public static BabyProfileDto of(BabyProfile profile) {
            BabyProfileDto dto = new BabyProfileDto(profile.getId(), profile.getImageUrl());
            profile.getBabyList().forEach(baby -> dto.babies.add(BabyDto.of(baby.getId(), baby.getName())));

            return dto;
        }
    }

    @Getter
    @RequiredArgsConstructor
    private static class BabyDto {
        private final Long babyId;
        private final String name;

        public static BabyDto of(Long babyId, String name) {
            return new BabyDto(babyId, name);
        }
    }
}
