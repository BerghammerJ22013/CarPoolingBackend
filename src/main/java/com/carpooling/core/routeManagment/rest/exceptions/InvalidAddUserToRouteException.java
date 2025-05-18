package com.carpooling.core.routeManagment.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAddUserToRouteException extends RuntimeException {
    public InvalidAddUserToRouteException(String message) {
        super(message);
    }
}
