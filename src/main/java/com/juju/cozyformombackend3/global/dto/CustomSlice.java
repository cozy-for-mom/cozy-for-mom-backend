package com.juju.cozyformombackend3.global.dto;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomSlice<T> {
    private final Long totalCount;
    private final List<T> data;

    public static <T> CustomSlice<T> of(Long totalCount, List<T> data) {

        return new CustomSlice<>(totalCount, data);
    }
}
