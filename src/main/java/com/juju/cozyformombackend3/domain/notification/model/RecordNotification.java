package com.juju.cozyformombackend3.domain.notification.model;

import java.util.ArrayList;
import java.util.List;

import com.juju.cozyformombackend3.global.model.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "record_notification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordNotification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_category", nullable = false)
    private NotificationCategory notificationCategory;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "recordNotification", cascade = {CascadeType.PERSIST,
        CascadeType.REMOVE}, orphanRemoval = true)
    private List<RecordNotificationTime> notifyTimeList = new ArrayList<>();

    @Override
    public void delete() {

    }

    @Builder
    private RecordNotification(Long userId, NotificationCategory notificationCategory, String title) {
        this.userId = userId;
        this.notificationCategory = notificationCategory;
        this.title = title;
        this.isActive = true;
    }

    public void addNotifyTime(RecordNotificationTime notificationTime) {
        this.notifyTimeList.add(notificationTime);
    }

    public void changeNotificationStatus(boolean isActive) {
        this.isActive = isActive;
    }

    public void changeTitle(String title) {
        if (!this.title.equals(title)) {
            this.title = title;
        }
    }

    public boolean getIsActive() {
        return isActive;
    }
}
