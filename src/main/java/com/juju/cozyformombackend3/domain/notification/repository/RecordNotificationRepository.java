package com.juju.cozyformombackend3.domain.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juju.cozyformombackend3.domain.notification.model.NotificationCategory;
import com.juju.cozyformombackend3.domain.notification.model.RecordNotification;

public interface RecordNotificationRepository extends JpaRepository<RecordNotification, Long> {
    List<RecordNotification> findAllByUserIdAndNotificationCategory(Long userId,
        NotificationCategory notificationCategory);
}
