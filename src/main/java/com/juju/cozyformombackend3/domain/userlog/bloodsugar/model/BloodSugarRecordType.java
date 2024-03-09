package com.juju.cozyformombackend3.domain.userlog.bloodsugar.model;

import java.util.Arrays;

public enum BloodSugarRecordType {
    BEFORE_BREAKFAST("아침 식전"), AFTER_BREAKFAST("아침 식후"),
    BEFORE_LUNCH("점심 식전"), AFTER_LUNCH("점심 식후"),
    BEFORE_DINNER("저녁 식전"), AFTER_DINNER("저녁 식후"),
    BEFORE_SLEEP("취침 전"), RANDOM("임의");

    private final String description;

    BloodSugarRecordType(String description) {
        this.description = description;
    }

    public static BloodSugarRecordType ofDescription(String description) {
        return Arrays.stream(BloodSugarRecordType.values())
            .filter(e -> e.getDescription().equals(description))
            .findAny()
            .orElse(null);
    }

    public String getDescription() {
        return description;
    }
}
