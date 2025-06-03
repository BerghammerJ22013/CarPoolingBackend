package com.carpooling.core.notificationManagment.rest.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationDto {
    @NotNull
    private Long senderId;
    @NotNull
    private Long receiverId;
    @NotNull
    private String message;
}
