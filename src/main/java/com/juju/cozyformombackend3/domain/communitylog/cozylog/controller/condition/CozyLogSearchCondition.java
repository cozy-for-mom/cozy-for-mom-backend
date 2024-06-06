package com.juju.cozyformombackend3.domain.communitylog.cozylog.controller.condition;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CozyLogSearchCondition {
    private final Long lastLogId;
    private final Long size;
    private final String keyword;
    private final CozyLogSort sort;

    public static CozyLogSearchCondition of(final Long lastLogId, final Long size, final String keyword,
        final CozyLogSort sort) {
        return new CozyLogSearchCondition(lastLogId, size, keyword, sort);
    }
}
