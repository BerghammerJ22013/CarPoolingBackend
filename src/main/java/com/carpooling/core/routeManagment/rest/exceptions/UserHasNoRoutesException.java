package com.carpooling.core.routeManagment.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ACCEPTED)
public class UserHasNoRoutesException extends RuntimeException {
    public UserHasNoRoutesException(String message) {
        super(message);
    }
}
