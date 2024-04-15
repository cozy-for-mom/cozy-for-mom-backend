package com.juju.cozyformombackend3.domain.notification.model;

import java.time.LocalTime;
import java.util.Arrays;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationRemindInterval {

    ONE_HOUR_AGO("one hour ago") {
        public LocalTime getNotifyTime(LocalTime targetTime) {
            return targetTime.minusHours(1);
        }
    },
    THIRTY_MINUTES_AGO("thirty minutes ago") {
        public LocalTime getNotifyTime(LocalTime targetTime) {
            return targetTime.minusMinutes(30);
        }
    },
    ON_TIME("on time") {
        public LocalTime getNotifyTime(LocalTime targetTime) {
            return targetTime;
        }
    };

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

    public abstract LocalTime getNotifyTime(LocalTime targetTime);
}

