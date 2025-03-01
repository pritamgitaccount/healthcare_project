package com.doctorbookingapp.repository;

import com.doctorbookingapp.entity.Notification;
import com.doctorbookingapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
  List<Notification> findByRecipientAndIsReadFalse(User recipient);




}