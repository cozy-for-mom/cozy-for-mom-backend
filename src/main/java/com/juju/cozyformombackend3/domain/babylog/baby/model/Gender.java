package com.juju.cozyformombackend3.domain.babylog.baby.model;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Gender {
    FEMALE("여자"), MALE("남자");

    private final String type;

    public static Gender ofType(String type) {
        return Arrays.stream(Gender.values())
            .filter(e -> e.getType().equals(type))
            .findAny()
            .orElse(null);
    }

    public String getType() {
        return this.type;
    }
}
