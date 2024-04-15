package com.juju.cozyformombackend3.domain.notification.model;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "record_notification_time")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordNotificationTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "remind_interval", nullable = false)
    private NotificationRemindInterval remindInterval;

    @Column(name = "notify_at", nullable = false)
    private LocalTime notifyAt;

    @Column(name = "target_time_at", nullable = false)
    private LocalTime targetTimeAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_notification_id")
    private RecordNotification recordNotification;

    @Builder
    private RecordNotificationTime(NotificationRemindInterval remindInterval, LocalTime notifyAt,
        LocalTime targetTimeAt, DayOfWeek dayOfWeek, RecordNotification recordNotification) {
        this.remindInterval = remindInterval;
        this.notifyAt = notifyAt;
        this.targetTimeAt = targetTimeAt;
        this.dayOfWeek = dayOfWeek;
        this.recordNotification = recordNotification;
    }
}
