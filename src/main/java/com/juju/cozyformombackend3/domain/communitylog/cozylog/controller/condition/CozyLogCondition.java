package com.juju.cozyformombackend3.domain.communitylog.cozylog.controller.condition;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CozyLogCondition {
    private final Long userId;
    private final Long size;
    private final Long lastLogId;
    private final CozyLogSort sort;
    private final Long writerId;
    private final String keyword;
}
