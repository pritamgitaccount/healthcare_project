package com.doctorbookingapp.service.impl;

import com.doctorbookingapp.entity.Notification;
import com.doctorbookingapp.entity.User;
import com.doctorbookingapp.repository.NotificationRepository;
import com.doctorbookingapp.repository.UserRepository;
import com.doctorbookingapp.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;


    @Override
    public void sendNotification(Long userId, String message) {
        User recipient = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User " + userId + "not found")
        );

        Notification notification = Notification.builder()
                .recipient(recipient)
                .message(message)
                .timestamp(LocalDateTime.now())
                .isRead(false)
                .build();

        notificationRepository.save(notification);

    }

    @Override
    public List<Notification> getUnreadNotifications(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User " + userId + "not found")
        );
        return notificationRepository.findByRecipientAndIsReadFalse(user);
    }


    @Override
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalStateException("No notifications found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}
