package com.juju.cozyformombackend3.domain.notification.controller.dto;

import java.time.LocalDate;
import java.util.List;

import com.juju.cozyformombackend3.domain.notification.model.NotificationRemindDateInterval;
import com.juju.cozyformombackend3.global.util.DateParser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CreateExaminationNotification {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private Long babyProfileId;
        private String examinationAt;
        private List<String> notifyAt;

        public List<NotificationRemindDateInterval> getNotifyAtList() {
            return notifyAt.stream().map(NotificationRemindDateInterval::ofType).toList();
        }

        public LocalDate getExaminationAt() {
            return DateParser.stringDateToLocalDate(examinationAt);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private final Long id;

        public static CreateExaminationNotification.Response of(final Long id) {
            return new CreateExaminationNotification.Response(id);
        }
    }
}
