package com.juju.cozyformombackend3.domain.notification.service;

import org.springframework.stereotype.Service;

import com.eatthepath.pushy.apns.ApnsClient;
import com.eatthepath.pushy.apns.PushNotificationResponse;
import com.eatthepath.pushy.apns.util.ApnsPayloadBuilder;
import com.eatthepath.pushy.apns.util.SimpleApnsPayloadBuilder;
import com.eatthepath.pushy.apns.util.SimpleApnsPushNotification;
import com.eatthepath.pushy.apns.util.TokenUtil;
import com.eatthepath.pushy.apns.util.concurrent.PushNotificationFuture;
import com.juju.cozyformombackend3.global.config.notification.apns.APNsProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class APNsService {
    private final APNsProperties apnsProperties;
    private final ApnsClient apnsClient;

    public void pushTestToAPNs() {
        log.info("APNs push test start");

        ApnsPayloadBuilder payloadBuilder = new SimpleApnsPayloadBuilder();
        payloadBuilder.setAlertTitle("test_title");
        payloadBuilder.setAlertBody("test_body");
        payloadBuilder.addCustomProperty("test_data_1", "abc");
        payloadBuilder.addCustomProperty("test_data_2", "def");
        final String payload = payloadBuilder.build();

        final String token = TokenUtil.sanitizeTokenString("destination_device_token");
        final SimpleApnsPushNotification pushNotification =
            new SimpleApnsPushNotification(token, apnsProperties.getClientId(), payload);
        log.info("pushNotification: {}", pushNotification);

        final PushNotificationFuture<SimpleApnsPushNotification, PushNotificationResponse<SimpleApnsPushNotification>>
            sendNotificationFuture = apnsClient.sendNotification(pushNotification);
        log.info("after sendNotification");

        sendNotificationFuture.whenComplete((response, cause) -> {
            if (response != null) {
                if (response.isAccepted()) {
                    System.out.println("Push notification accepted by APNs gateway.");
                    log.info("response: {}", response);
                    log.info("payload: {}", response.getPushNotification().getPayload());
                } else {
                    System.out.println("Notification rejected by the APNs gateway: " +
                                       response.getRejectionReason());

                    response.getTokenInvalidationTimestamp().ifPresent(timestamp -> {
                        System.out.println("\t…and the token is invalid as of " + timestamp);
                    });
                }
            } else {
                // Something went wrong when trying to send the notification to the APNs server.
                // Note that this is distinct from a rejection from the server, and indicates
                // that something went wrong when actually sending the notification or waiting for a reply.
                cause.printStackTrace();
            }
        });

        // sendNotificationFuture.addListener(future -> {
        //     try {
        //         PushNotificationResponse<SimpleApnsPushNotification> asyncResponse = future.get();
        //         System.out.println(asyncResponse);
        //         // Print rejection reason
        //         System.out.println(asyncResponse.getRejectionReason());
        //         // Print original payload
        //         System.out.println(asyncResponse.getPushNotification().getPayload());
        //
        //     } catch (InterruptedException | ExecutionException e) {
        //         e.printStackTrace();
        //     }
        // });
        log.info("==========================APNs push test end============================");

    }
}
