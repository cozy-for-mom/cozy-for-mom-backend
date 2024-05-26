package com.juju.cozyformombackend3.global.config.notification;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ConfigurationProperties(prefix = "notification.apns")
@Getter
@RequiredArgsConstructor
public class APNsProperties {
    private final String clientId;
    private final String certPath;
    private final String certPassword;
}
