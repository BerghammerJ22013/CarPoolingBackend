package com.carpooling.core.notificationManagment.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidRemoveNotificationException extends RuntimeException {
    public InvalidRemoveNotificationException(String message) {
        super(message);
    }
}
