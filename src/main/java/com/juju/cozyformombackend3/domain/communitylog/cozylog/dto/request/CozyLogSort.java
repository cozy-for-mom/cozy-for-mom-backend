package com.juju.cozyformombackend3.domain.communitylog.cozylog.dto.request;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CozyLogSort {
    LATELY("lately"),
    COMMENT("comment"),
    HOT("hot");

    private final String sortKeyword;

    public static CozyLogSort of(String sortKeyword) {
        if (sortKeyword.isBlank())
            return LATELY;
        return Arrays.stream(values())
            .filter(keyword -> keyword.sortKeyword.equals(sortKeyword))
            .findFirst()
            .orElse(LATELY);
    }
}
