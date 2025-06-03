package com.carpooling.core.notificationManagment.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class InvalidGetNotificationsByReceiverOfTodayException extends RuntimeException {
    public InvalidGetNotificationsByReceiverOfTodayException(String message) {
        super(message);
    }
}
