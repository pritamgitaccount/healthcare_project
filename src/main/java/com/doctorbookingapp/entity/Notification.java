package com.doctorbookingapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String message;
    private LocalDateTime timestamp;
    private boolean isRead;

    @ManyToOne
    private User recipient;  // Linked to a user

}