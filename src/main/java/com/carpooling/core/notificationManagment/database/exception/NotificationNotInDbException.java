package com.carpooling.core.notificationManagment.database.exception;

public class NotificationNotInDbException extends Exception {
    public NotificationNotInDbException(String message) {
        super(message);
    }
}
