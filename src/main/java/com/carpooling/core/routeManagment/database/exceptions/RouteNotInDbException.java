package com.carpooling.core.routeManagment.database.exceptions;

public class RouteNotInDbException extends Exception {
    public RouteNotInDbException(String message) {
        super(message);
    }
}
