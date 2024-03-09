package com.juju.cozyformombackend3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class CozyForMomBackend3Application {

    public static void main(String[] args) {
        SpringApplication.run(CozyForMomBackend3Application.class, args);
    }

}
