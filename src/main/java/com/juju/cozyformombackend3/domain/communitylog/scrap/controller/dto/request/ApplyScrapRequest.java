package com.juju.cozyformombackend3.domain.communitylog.scrap.controller.dto.request;

import com.juju.cozyformombackend3.domain.communitylog.scrap.model.Scrap;
import com.juju.cozyformombackend3.domain.user.model.User;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ApplyScrapRequest {
    @Min(1)
    private Long cozyLogId;
    @NotNull
    private boolean isScraped;

    public Scrap toScrap(User user) {
        return Scrap.builder()
            .user(user)
            .cozyLogId(cozyLogId)
            .build();
    }

    public Boolean getIsScraped() {
        return isScraped;
    }
}
