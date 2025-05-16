package com.carpooling.core.userManagement.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidChangePasswordException extends RuntimeException {
    public InvalidChangePasswordException(String message) {
        super(message);
    }
}
