package com.carpooling.core.notificationManagment.database.entities;

import com.carpooling.core.userManagement.database.entities.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notifications")
public class NotificationEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private UserEntity sender;

    @ManyToOne
    private UserEntity receiver;

    private String message;
    private LocalDateTime timeStamp;
}
