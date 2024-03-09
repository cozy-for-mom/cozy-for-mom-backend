package com.juju.cozyformombackend3.domain.user.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class UpdateRecentBabyProfileRequest {
    @Min(1)
    private Long babyProfileId;
}
