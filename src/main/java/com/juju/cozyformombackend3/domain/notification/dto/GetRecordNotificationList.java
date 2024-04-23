package com.juju.cozyformombackend3.domain.notification.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.juju.cozyformombackend3.domain.notification.model.RecordNotification;
import com.juju.cozyformombackend3.domain.notification.model.RecordNotificationTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class GetRecordNotificationList {
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Response {
        private final List<RecordNotificationDto> notification;

        public static Response of(List<RecordNotification> findNotificationList) {
            return new Response(findNotificationList.stream().map(noti -> {
                List<RecordNotificationTime> notifyTimeList = noti.getNotifyTimeList();
                Set<String> notifyAt = new HashSet<>();
                Set<String> targetTimeAt = new HashSet<>();
                Set<String> daysOfWeek = new HashSet<>();
                notifyTimeList.forEach(time -> {
                    notifyAt.add(time.getRemindInterval().getType());
                    targetTimeAt.add(time.getTargetTimeAt().toString());
                    daysOfWeek.add(time.getDayOfWeek().getType());
                });

                return RecordNotificationDto.of(noti.getTitle(), null, noti.getIsActive(),
                    new ArrayList<>(notifyAt), new ArrayList<>(targetTimeAt), new ArrayList<>(daysOfWeek));
            }).toList());
        }

        @Getter
        @AllArgsConstructor
        private static class RecordNotificationDto {
            private final String title;
            private final Integer targetCount;
            private final boolean isActive;
            private final List<String> notifyAt;
            private final List<String> targetTimeAt;
            private final List<String> daysOfWeek;

            public boolean getIsActive() {
                return this.isActive;
            }

            public static RecordNotificationDto of(final String title, final Integer targetCount,
                final boolean isActive,
                final List<String> notifyAt, final List<String> targetTimeAt, final List<String> daysOfWeek) {
                return new RecordNotificationDto(title, targetCount, isActive, notifyAt, targetTimeAt, daysOfWeek);
            }
        }
    }
}
