package com.juju.cozyformombackend3.domain.notification.model;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationCategory {
    SUPPLEMENT("supplement"), BLOODSUGAR("bloodsugar"),
    EXAMINATION("examination");
    // ,
    // EVENT("event"), ETC("etc");

    private final String type;

    public static NotificationCategory ofType(String type) {
        return Arrays.stream(NotificationCategory.values())
            .filter(e -> e.getType().equals(type))
            .findAny()
            .orElse(SUPPLEMENT);
    }

    public String getType() {
        return type;
    }
}
