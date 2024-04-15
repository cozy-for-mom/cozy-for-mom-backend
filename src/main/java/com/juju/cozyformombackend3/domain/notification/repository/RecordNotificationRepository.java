package com.juju.cozyformombackend3.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juju.cozyformombackend3.domain.notification.model.RecordNotification;

public interface RecordNotificationRepository extends JpaRepository<RecordNotification, Long> {
}
