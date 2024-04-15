package com.juju.cozyformombackend3.domain.notification.model;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationRemindInterval {

    ONE_HOUR_AGO("one hour ago"),
    THIRTY_MINUTES_AGO("thirty minutes ago"),
    ON_TIME("on time");

    private final String type;

    public static NotificationRemindInterval ofType(String type) {
        return Arrays.stream(NotificationRemindInterval.values())
            .filter(e -> e.getType().equals(type))
            .findAny()
            .orElse(null);
    }

    public String getType() {
        return type;
    }
}

