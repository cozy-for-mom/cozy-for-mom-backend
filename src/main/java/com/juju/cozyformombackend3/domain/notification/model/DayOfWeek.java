package com.juju.cozyformombackend3.domain.notification.model;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DayOfWeek {
    MONDAY("mon"),
    TUESDAY("tue"),
    WEDNESDAY("wed"),
    THURSDAY("thu"),
    FRIDAY("fri"),
    SATURDAY("sat"),
    SUNDAY("sun"),
    ALL("all");

    private final String type;

    public static DayOfWeek ofType(String type) {
        return Arrays.stream(DayOfWeek.values())
            .filter(e -> e.getType().equals(type))
            .findAny()
            .orElse(ALL);
    }

    public String getType() {
        return type;
    }
}
