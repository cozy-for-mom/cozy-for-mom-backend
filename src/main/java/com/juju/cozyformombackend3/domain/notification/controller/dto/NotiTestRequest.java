package com.juju.cozyformombackend3.domain.notification.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotiTestRequest {
    private Aps aps;

    @Getter
    @AllArgsConstructor
    public static class Aps {
        private Alert alert;
        private String category;

    }

    @Getter
    @AllArgsConstructor
    public static class Alert {
        private String title;
        private String subtitle;
        private String body;
    }
}
