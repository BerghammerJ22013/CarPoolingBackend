package com.carpooling.core.userManagement.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidRegisterException extends RuntimeException {
    public InvalidRegisterException(String message) {
        super(message);
    }
}
