package com.juju.cozyformombackend3.domain.notification.dto;

import java.time.LocalTime;
import java.util.List;

import com.juju.cozyformombackend3.domain.notification.model.DayOfWeek;
import com.juju.cozyformombackend3.domain.notification.model.NotificationRemindTimeInterval;
import com.juju.cozyformombackend3.global.util.TimeParser;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ModifyRecordNotification {

    @Getter
    @AllArgsConstructor
    public static class Request {

        private final String title;
        private final List<String> notifyAt;
        private final List<String> targetTimeAt;
        private final List<String> daysOfWeek;

        public List<NotificationRemindTimeInterval> getNotifyAtList() {
            return notifyAt.stream().map(NotificationRemindTimeInterval::ofType).toList();
        }

        public List<LocalTime> getTargetTimeAtList() {
            return targetTimeAt.stream().map(TimeParser::stringToLocalTime).toList();
        }

        public List<DayOfWeek> getDaysOfWeekList() {
            return daysOfWeek.stream().map(DayOfWeek::ofType).toList();
        }

        @Override
        public String toString() {
            return "Request{" +
                ", title='" + title + '\'' +
                ", notifyAt=" + notifyAt +
                ", targetTimeAt=" + targetTimeAt +
                ", daysOfWeek=" + daysOfWeek +
                '}';
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private final Long id;

        public static Response of(final Long id) {
            return new Response(id);
        }
    }
}
