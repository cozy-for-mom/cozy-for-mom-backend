package com.juju.cozyformombackend3.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ModifyRecordNotificationActive {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private boolean isActive;

        public boolean getIsActive() {
            return this.isActive;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private final Long id;

        public static ModifyRecordNotificationActive.Response of(final Long id) {
            return new ModifyRecordNotificationActive.Response(id);
        }
    }
}
