package com.carpooling.core.notificationManagment;

import com.carpooling.core.notificationManagment.database.entities.NotificationEntity;
import com.carpooling.core.notificationManagment.database.exception.NoNotificationsFoundException;
import com.carpooling.core.notificationManagment.database.exception.NotificationNotInDbException;
import com.carpooling.core.notificationManagment.rest.dtos.NotificationDto;
import com.carpooling.core.notificationManagment.rest.exception.InvalidGetNotificationsByReceiverException;
import com.carpooling.core.notificationManagment.rest.exception.InvalidGetNotificationsByReceiverOfTodayException;
import com.carpooling.core.notificationManagment.rest.exception.InvalidRemoveNotificationException;
import com.carpooling.core.notificationManagment.rest.exception.InvalidSendNotificationException;
import com.carpooling.core.notificationManagment.rest.resources.NotificationResource;
import com.carpooling.core.userManagement.database.exceptions.UserNotInDbException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class NotificationDataService {
    @Autowired
    private NotificationManager notificationManager;

    public List<NotificationResource> getNotificationsByReceiver(Long userId) {
        List<NotificationResource> result = new ArrayList<>();
        try {
            for (NotificationEntity noEnt : notificationManager.getNotificationsByReceiver(userId)){
                result.add(convertNotificationEntityToNotificationResource(noEnt));
            }
        } catch (UserNotInDbException | NoNotificationsFoundException e) {
            throw new InvalidGetNotificationsByReceiverException(e.getMessage());
        }
        return result;
    }

    public List<NotificationResource> getNotificationsByReceiverOfToday(Long userId) {
        List<NotificationResource> result = new ArrayList<>();
        try {
            for (NotificationEntity noEnt : notificationManager.getNotificationsByReceiverOfToday(userId)){
                result.add(convertNotificationEntityToNotificationResource(noEnt));
            }
        } catch (UserNotInDbException | NoNotificationsFoundException e) {
            throw new InvalidGetNotificationsByReceiverOfTodayException(e.getMessage());
        }
        return result;
    }

    public NotificationResource sendNotification(NotificationDto notificationDto) {
        try {
            return convertNotificationEntityToNotificationResource(notificationManager.sendNotification(notificationDto));
        } catch (UserNotInDbException e) {
            throw new InvalidSendNotificationException(e.getMessage());
        }
    }

    public void removeNotification(Long notificationId) {
        try {
            notificationManager.removeNotification(notificationId);
        } catch (NotificationNotInDbException e) {
            throw new InvalidRemoveNotificationException(e.getMessage());
        }
    }

    private NotificationResource convertNotificationEntityToNotificationResource(NotificationEntity notificationEntity){
        NotificationResource resource = new NotificationResource();
        resource.setId(notificationEntity.getId());
        resource.setSenderfullName(notificationEntity.getSender().getFullName());
        resource.setReceiverfullName(notificationEntity.getReceiver().getFullName());
        resource.setMessage(notificationEntity.getMessage());
        resource.setTimestamp(notificationEntity.getTimeStamp());
        return resource;
    }



}
