package com.carpooling.core.routeManagment.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidGetRoutesByUserException extends RuntimeException {
    public InvalidGetRoutesByUserException(String message) {
        super(message);
    }
}
