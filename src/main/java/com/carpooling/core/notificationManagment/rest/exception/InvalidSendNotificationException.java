package com.carpooling.core.notificationManagment.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidSendNotificationException extends RuntimeException {
    public InvalidSendNotificationException(String message) {
        super(message);
    }
}
