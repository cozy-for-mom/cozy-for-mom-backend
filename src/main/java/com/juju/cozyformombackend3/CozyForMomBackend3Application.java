package com.juju.cozyformombackend3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@ConfigurationPropertiesScan
public class CozyForMomBackend3Application {

    public static void main(String[] args) {
        SpringApplication.run(CozyForMomBackend3Application.class, args);
    }

}
