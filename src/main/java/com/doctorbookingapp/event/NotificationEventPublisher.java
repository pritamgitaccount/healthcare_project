package com.doctorbookingapp.event;

import com.doctorbookingapp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishNotificationEvent(User recipient, String message) {
        NotificationEvent notificationEvent = new NotificationEvent(this, recipient, message);
        applicationEventPublisher.publishEvent(notificationEvent);
    }
}
