package com.doctorbookingapp.event;

import com.doctorbookingapp.entity.Notification;
import com.doctorbookingapp.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationEventListener {
    private final NotificationRepository notificationRepository;

    @Async
    @EventListener
    public void handleNotificationEvent(NotificationEvent event) {
        log.info("Processing notification for user: {}",
                event.getRecipient() != null ? event.getRecipient().getUsername() : "Unknown User");

        var notification = Notification.builder()
                .recipient(event.getRecipient())
                .message(event.getMessage())
                .timestamp(now())
                .isRead(false)
                .build();

        notificationRepository.save(notification);
    }
}
