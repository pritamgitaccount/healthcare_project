package com.doctorbookingapp.service;

import com.doctorbookingapp.entity.Notification;
import java.util.List;

public interface NotificationService {

    void sendNotification(Long userId, String message);
    List<Notification> getUnreadNotifications(Long userId);
    void markAsRead(Long notificationId);
}
