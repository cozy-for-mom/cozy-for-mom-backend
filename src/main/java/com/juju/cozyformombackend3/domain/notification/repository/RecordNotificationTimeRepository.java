package com.juju.cozyformombackend3.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juju.cozyformombackend3.domain.notification.model.DayOfWeek;
import com.juju.cozyformombackend3.domain.notification.model.RecordNotificationTime;

public interface RecordNotificationTimeRepository extends JpaRepository<RecordNotificationTime, Long> {
    void deleteAllByRecordNotificationIdAndDayOfWeek(Long notificationId, DayOfWeek day);
}
