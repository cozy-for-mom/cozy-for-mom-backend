package com.juju.cozyformombackend3.domain.notification.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.notification.dto.CreateExaminationNotification;
import com.juju.cozyformombackend3.domain.notification.dto.CreateRecordNotification;
import com.juju.cozyformombackend3.domain.notification.dto.ModifyExaminationNotification;
import com.juju.cozyformombackend3.domain.notification.dto.ModifyRecordNotification;
import com.juju.cozyformombackend3.domain.notification.dto.ModifyRecordNotificationActive;
import com.juju.cozyformombackend3.domain.notification.error.NotificationErrorCode;
import com.juju.cozyformombackend3.domain.notification.model.DayOfWeek;
import com.juju.cozyformombackend3.domain.notification.model.ExaminationNotification;
import com.juju.cozyformombackend3.domain.notification.model.ExaminationNotificationDate;
import com.juju.cozyformombackend3.domain.notification.model.NotificationRemindTimeInterval;
import com.juju.cozyformombackend3.domain.notification.model.RecordNotification;
import com.juju.cozyformombackend3.domain.notification.model.RecordNotificationTime;
import com.juju.cozyformombackend3.domain.notification.repository.ExaminationNotificationRepository;
import com.juju.cozyformombackend3.domain.notification.repository.RecordNotificationRepository;
import com.juju.cozyformombackend3.domain.notification.repository.RecordNotificationTimeRepository;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {
    private final RecordNotificationRepository recordNotificationRepository;
    private final RecordNotificationTimeRepository recordNotificationTimeRepository;
    private final ExaminationNotificationRepository examinationNotificationRepository;

    @Transactional
    public CreateRecordNotification.Response saveRecordNotification(Long userId,
        CreateRecordNotification.Request request) {

        final RecordNotification saveNotification = recordNotificationRepository.save(RecordNotification.builder()
            .userId(userId)
            .notificationCategory(request.getType())
            .title(request.getTitle())
            // .targetId()
            .build());

        request.getTargetTimeAtList().forEach(targetTime -> {
            for (DayOfWeek day : request.getDaysOfWeekList()) {
                for (NotificationRemindTimeInterval remindInterval : request.getNotifyAtList()) {
                    saveNotification.addNotifyTime(RecordNotificationTime.builder()
                        .remindInterval(remindInterval)
                        .notifyAt(remindInterval.getNotifyTime(targetTime))
                        .targetTimeAt(targetTime)
                        .dayOfWeek(day)
                        .recordNotification(saveNotification)
                        .build());
                }
            }
        });

        return CreateRecordNotification.Response.of(saveNotification.getId());
    }

    @Transactional
    public ModifyRecordNotificationActive.Response modifyRecordNotificationActive(
        Long notificationId, ModifyRecordNotificationActive.Request request) {

        final RecordNotification findNotification = recordNotificationRepository.findById(notificationId)
            .orElseThrow(() -> new BusinessException(NotificationErrorCode.NOT_FOUND_NOTIFICATION));
        findNotification.changeNotificationStatus(request.getIsActive());

        return ModifyRecordNotificationActive.Response.of(findNotification.getId());
    }

    @Transactional
    public ModifyRecordNotification.Response modifyRecordNotification(Long notificationId,
        ModifyRecordNotification.Request request) {
        final RecordNotification findNotification = recordNotificationRepository.findById(notificationId)
            .orElseThrow(() -> new BusinessException(NotificationErrorCode.NOT_FOUND_NOTIFICATION));

        findNotification.changeTitle(request.getTitle());

        List<RecordNotificationTime> requestNotificationTimeList = new ArrayList<>();
        request.getTargetTimeAtList().forEach(targetTime -> {
            for (DayOfWeek day : request.getDaysOfWeekList()) {
                for (NotificationRemindTimeInterval remindInterval : request.getNotifyAtList()) {
                    requestNotificationTimeList.add(RecordNotificationTime.builder()
                        .remindInterval(remindInterval)
                        .notifyAt(remindInterval.getNotifyTime(targetTime))
                        .targetTimeAt(targetTime)
                        .dayOfWeek(day)
                        .build());
                }
            }
        });

        List<RecordNotificationTime> findNotificationTime = findNotification.getNotifyTimeList();
        for (int i = 0; i < requestNotificationTimeList.size(); i++) {
            RecordNotificationTime notificationTime = requestNotificationTimeList.get(i);
            if (!findNotificationTime.contains(notificationTime)) {
                notificationTime.applyNotification(findNotification);
                findNotification.addNotifyTime(notificationTime);
            }
        }

        List<RecordNotificationTime> itemsToRemove = new ArrayList<>();
        for (RecordNotificationTime notificationTime : findNotificationTime) {
            if (!requestNotificationTimeList.contains(notificationTime)) {
                itemsToRemove.add(notificationTime);
            }
        }
        findNotificationTime.removeAll(itemsToRemove);

        return ModifyRecordNotification.Response.of(findNotification.getId());
    }

    private void removeOverLapElement(Set s1, Set s2) {
        Set<Integer> overLapElements = new HashSet<>(s1);
        overLapElements.retainAll(s2);
        s1.removeAll(overLapElements);
        s2.removeAll(overLapElements);

    }

    @Transactional
    public void removeRecordNotification(Long notificationId) {
        recordNotificationRepository.deleteById(notificationId);
    }

    @Transactional
    public CreateExaminationNotification.Response saveExaminationNotification(Long userId,
        CreateExaminationNotification.Request request) {
        final ExaminationNotification saveNotification = examinationNotificationRepository
            .save(ExaminationNotification.builder()
                .userId(userId)
                .babyProfileId(request.getBabyProfileId())
                .targetDateAt(request.getExaminationAt())
                .build());

        request.getNotifyAtList().forEach(remindDateInterval -> {
            saveNotification.addNotifyDate(ExaminationNotificationDate.builder()
                .remindInterval(remindDateInterval)
                .notifyAt(remindDateInterval.getNotifyDate(request.getExaminationAt()))
                .examinationNotification(saveNotification)
                .build());
        });

        return CreateExaminationNotification.Response.of(saveNotification.getId());
    }

    @Transactional
    public ModifyExaminationNotification.Response modifyExaminationNotification(Long userId, Long notificationId,
        ModifyExaminationNotification.Request request) {
        final ExaminationNotification findNotification = examinationNotificationRepository.findById(notificationId)
            .orElseThrow(() -> new BusinessException(NotificationErrorCode.NOT_FOUND_NOTIFICATION));

        findNotification.changeTargetDate(request.getExaminationAt());

        List<ExaminationNotificationDate> requestNotificationDateList = new ArrayList<>();
        request.getNotifyAtList().forEach(remindDateInterval ->
            requestNotificationDateList.add(ExaminationNotificationDate.builder()
                .remindInterval(remindDateInterval)
                .notifyAt(remindDateInterval.getNotifyDate(request.getExaminationAt()))
                .build()));

        List<ExaminationNotificationDate> findNotificationDate = findNotification.getNotifyDateList();
        for (ExaminationNotificationDate notifyDate : requestNotificationDateList) {
            if (!findNotificationDate.contains(notifyDate)) {
                notifyDate.applyNotification(findNotification);
                findNotification.addNotifyDate(notifyDate);
            }
        }

        List<ExaminationNotificationDate> itemsToRemove = new ArrayList<>();
        for (ExaminationNotificationDate notifyDate : findNotificationDate) {
            if (!requestNotificationDateList.contains(notifyDate)) {
                itemsToRemove.add(notifyDate);
            }
        }
        findNotificationDate.removeAll(itemsToRemove);

        return ModifyExaminationNotification.Response.of(findNotification.getId());
    }

    @Transactional
    public void removeExaminationNotification(Long notificationId) {
        examinationNotificationRepository.deleteById(notificationId);
    }

    // private final
}
