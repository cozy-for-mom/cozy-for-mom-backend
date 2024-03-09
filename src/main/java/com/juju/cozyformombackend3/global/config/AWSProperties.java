package com.juju.cozyformombackend3.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ConfigurationProperties(prefix = "cloud.aws")
@Getter
@RequiredArgsConstructor
public class AWSProperties {
    private final S3 s3;
    private final Region region;
    private final Credentials credentials;

    public String getS3Bucket() {
        return s3.getBucket();
    }

    public String getRegion() {
        return region.getRegion();
    }

    public String getAccessKey() {
        return credentials.getAccessKey();
    }

    public String getSecretKey() {
        return credentials.getSecretKey();
    }

    @Getter
    @RequiredArgsConstructor
    public static class S3 {
        private final String bucket;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Region {
        private final String region;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Credentials {
        private final String accessKey;
        private final String secretKey;
    }
}
