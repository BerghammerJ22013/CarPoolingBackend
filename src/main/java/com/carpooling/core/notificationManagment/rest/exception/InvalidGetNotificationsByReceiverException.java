package com.carpooling.core.notificationManagment.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class InvalidGetNotificationsByReceiverException extends RuntimeException {
    public InvalidGetNotificationsByReceiverException(String message) {
        super(message);
    }
}
