package com.juju.cozyformombackend3.global.auth.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserRole {
    USER("user"),
    GUEST("guest");

    private final String type;
}
