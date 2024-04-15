package com.juju.cozyformombackend3.domain.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juju.cozyformombackend3.domain.notification.model.ExaminationNotification;

public interface ExaminationNotificationRepository extends JpaRepository<ExaminationNotification, Long> {
}
