package com.juju.cozyformombackend3.domain.notification.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juju.cozyformombackend3.domain.notification.dto.CreateRecordNotification;
import com.juju.cozyformombackend3.domain.notification.dto.ModifyRecordNotificationActive;
import com.juju.cozyformombackend3.domain.notification.error.NotificationErrorCode;
import com.juju.cozyformombackend3.domain.notification.model.DayOfWeek;
import com.juju.cozyformombackend3.domain.notification.model.NotificationRemindInterval;
import com.juju.cozyformombackend3.domain.notification.model.RecordNotification;
import com.juju.cozyformombackend3.domain.notification.model.RecordNotificationTime;
import com.juju.cozyformombackend3.domain.notification.repository.RecordNotificationRepository;
import com.juju.cozyformombackend3.global.error.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {
    private final RecordNotificationRepository recordNotificationRepository;

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
                for (NotificationRemindInterval remindInterval : request.getNotifyAtList()) {
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

    // private final
}
