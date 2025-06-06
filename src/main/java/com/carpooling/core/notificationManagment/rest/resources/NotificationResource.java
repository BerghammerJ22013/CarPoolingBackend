package com.carpooling.core.notificationManagment.rest.resources;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResource {
    private Long id;
    private String senderfullName;
    private String receiverfullName;
    private String message;
    private LocalDateTime timestamp;
}
