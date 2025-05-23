package com.carpooling.core.routeManagment.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRouteRemovalException extends RuntimeException {
    public InvalidRouteRemovalException(String message) {
        super(message);
    }
}
