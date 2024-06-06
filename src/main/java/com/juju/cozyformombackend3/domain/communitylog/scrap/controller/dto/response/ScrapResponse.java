package com.juju.cozyformombackend3.domain.communitylog.scrap.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ScrapResponse {
    private final Long cozyLogId;

    public static ScrapResponse of(final Long cozyLogId) {
        return new ScrapResponse(cozyLogId);
    }
}
