package com.juju.cozyformombackend3.domain.notification.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.juju.cozyformombackend3.global.model.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "examination_notification")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExaminationNotification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "baby_profile_id", nullable = false)
    private Long babyProfileId;

    @OneToMany(mappedBy = "examinationNotification", cascade = {CascadeType.PERSIST,
        CascadeType.REMOVE}, orphanRemoval = true)
    private List<ExaminationNotificationTime> notifyTimeList = new ArrayList<>();

    @Column(name = "target_date_at", nullable = false)
    private LocalDate targetDateAt;

    @Override
    public void delete() {

    }

    @Builder
    private ExaminationNotification(Long userId, Long babyProfileId, LocalDate targetDateAt) {
        this.userId = userId;
        this.babyProfileId = babyProfileId;
        this.targetDateAt = targetDateAt;
    }

    public void addNotifyDate(ExaminationNotificationTime notifyDate) {
        this.notifyTimeList.add(notifyDate);
    }
}
