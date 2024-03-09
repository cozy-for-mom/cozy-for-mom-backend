package com.juju.cozyformombackend3.domain.communitylog.cozylog.model;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CozyLogMode {
    PUBLIC("PUBLIC"), PRIVATE("PRIVATE");

    private final String type;

    public static CozyLogMode ofType(String type) {
        return Arrays.stream(CozyLogMode.values())
            .filter(e -> e.getType().equals(type))
            .findAny()
            .orElse(null);
    }

    public String getType() {
        return type;
    }
}
