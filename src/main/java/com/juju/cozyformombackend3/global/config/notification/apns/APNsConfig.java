package com.juju.cozyformombackend3.global.config.notification.apns;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.eatthepath.pushy.apns.ApnsClient;
import com.eatthepath.pushy.apns.ApnsClientBuilder;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class APNsConfig {
    private final APNsProperties apnsProperties;

    @Bean
    public ApnsClient cozyApnsClient() {

        try {
            return new ApnsClientBuilder()
                .setApnsServer(ApnsClientBuilder.DEVELOPMENT_APNS_HOST)
                // .setClientCredentials(new File("/path/to/certificate.p12"), "p12-file-password")
                .setClientCredentials(new ClassPathResource(apnsProperties.getCertPath()).getInputStream(),
                    apnsProperties.getCertPassword())
                .build();
        } catch (IOException e) {
            throw new RuntimeException("apns 인증서 getInputStream 실패");
        }
    }
}
