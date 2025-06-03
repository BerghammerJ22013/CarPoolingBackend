package com.carpooling.core.notificationManagment.rest.resources;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResource {
    private Long id;
    private String senderFullname;
    private String receiverFullname;
    private String message;
    private LocalDateTime timestamp;
}
