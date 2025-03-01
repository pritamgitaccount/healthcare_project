package com.doctorbookingapp.controller;

import com.doctorbookingapp.entity.Notification;
import com.doctorbookingapp.entity.User;
import com.doctorbookingapp.service.NotificationService;
import com.doctorbookingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService; // Assuming you have a UserService to fetch users


    //Send a notification
    public ResponseEntity<String> sendNotification(
            @PathVariable Long userId,
            @RequestBody String notification) {
        User recipient = userService.getUserById(userId);
        notificationService.sendNotification(recipient.getId(), notification);
        return ResponseEntity.ok("Notification sent successfully");
    }


    // ✅ Get unread notifications for a user
    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        List<Notification> notifications = notificationService.getUnreadNotifications(user.getId());
        return ResponseEntity.ok(notifications);
    }

    // ✅ Mark a notification as read
    @PutMapping("/mark-as-read/{notificationId}")
    public ResponseEntity<String> markNotificationAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok("Notification marked as read.");
    }
}
