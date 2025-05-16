package com.carpooling.core.userManagement.database.exceptions;

public class UserNotInDbException extends Exception {
    public UserNotInDbException(String message) {
        super(message);
    }
}
