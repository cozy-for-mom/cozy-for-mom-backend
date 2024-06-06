package com.juju.cozyformombackend3.domain.user.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateRecentBabyProfileResponse {
    private final Long babyProfileId;

    public static UpdateRecentBabyProfileResponse of(final Long babyProfileId) {
        return new UpdateRecentBabyProfileResponse(babyProfileId);
    }
}
