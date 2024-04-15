package com.juju.cozyformombackend3.domain.notification.model;

import java.time.LocalDate;
import java.util.Arrays;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationRemindDateInterval {
    ONE_DAY_AGO("one day ago") {
        public LocalDate getNotifyDate(LocalDate targetDate) {
            return targetDate.minusDays(1);
        }
    },
    TWO_DAY_AGO("two day ago") {
        public LocalDate getNotifyDate(LocalDate targetDate) {
            return targetDate.minusDays(2);
        }
    },

    ONE_WEEK_AGO("one week ago") {
        public LocalDate getNotifyDate(LocalDate targetDate) {
            return targetDate.minusWeeks(1);
        }
    },
    ON_DAY("on day") {
        public LocalDate getNotifyDate(LocalDate targetDate) {
            return targetDate;
        }
    },

    NONE("none") {
        public LocalDate getNotifyDate(LocalDate targetDate) {
            return null;
        }
    };

    private final String type;

    public static NotificationRemindDateInterval ofType(String type) {
        return Arrays.stream(NotificationRemindDateInterval.values())
            .filter(e -> e.getType().equals(type))
            .findAny()
            .orElse(NONE);
    }

    public String getType() {
        return type;
    }

    public abstract LocalDate getNotifyDate(LocalDate targetDate);
}

