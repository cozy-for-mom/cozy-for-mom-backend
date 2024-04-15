package com.juju.cozyformombackend3.domain.notification.model;

import java.time.LocalDate;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "examination_notification_time")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExaminationNotificationTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "remind_interval", nullable = false)
    private NotificationRemindDateInterval remindInterval;

    @Column(name = "notify_at", nullable = false)
    private LocalDate notifyAt;

    @Column(name = "target_date_at", nullable = false)
    private LocalDate targetDateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examination_notification_id")
    private ExaminationNotification examinationNotification;

}
