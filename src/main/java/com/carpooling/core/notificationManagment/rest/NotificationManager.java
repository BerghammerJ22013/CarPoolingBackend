package com.carpooling.core.notificationManagment.rest;

import com.carpooling.core.notificationManagment.database.entities.NotificationEntity;
import com.carpooling.core.notificationManagment.database.exception.NoNotificationsFoundException;
import com.carpooling.core.notificationManagment.database.exception.NotificationNotInDbException;
import com.carpooling.core.notificationManagment.database.repositories.NotificationRepository;
import com.carpooling.core.notificationManagment.rest.dtos.NotificationDto;
import com.carpooling.core.userManagement.database.entities.UserEntity;
import com.carpooling.core.userManagement.database.exceptions.UserNotInDbException;
import com.carpooling.core.userManagement.rest.UserManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class NotificationManager {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserManager userManager;

    public List<NotificationEntity> getNotificationsByReceiver(Long userId) throws UserNotInDbException, NoNotificationsFoundException {
        UserEntity receiver = userManager.getUserById(userId);

        Optional<List<NotificationEntity>> result = notificationRepository.findByReceiver(receiver);
        if(result.isEmpty()){
            throw new NoNotificationsFoundException("No notifications found");
        }

        return result.get();
    }

    public List<NotificationEntity> getNotificationsByReceiverOfToday(Long userId) throws UserNotInDbException, NoNotificationsFoundException {
        UserEntity receiver = userManager.getUserById(userId);

        Optional<List<NotificationEntity>> result = notificationRepository.findByReceiverOfToday(receiver);
        if(result.isEmpty()){
            throw new NoNotificationsFoundException("No notifications of today found");
        }

        return result.get();
    }

    public NotificationEntity sendNotification(NotificationDto notificationDto) throws UserNotInDbException {
        return notificationRepository.save(convertNotificationDtoToNotificationEntity(notificationDto));
    }

    public void removeNotification(Long notificationId) throws NotificationNotInDbException {
        NotificationEntity notEnt = notificationRepository.findById(notificationId).orElseThrow(()
        -> new NotificationNotInDbException("Notification not found"));

        notificationRepository.delete(notEnt);
    }

    private NotificationEntity convertNotificationDtoToNotificationEntity(NotificationDto notificationDto) throws UserNotInDbException {
        NotificationEntity notEnt = new NotificationEntity();
        notEnt.setReceiver(userManager.getUserById(notificationDto.getReceiverId()));
        notEnt.setSender(userManager.getUserById(notificationDto.getSenderId()));
        notEnt.setMessage(notificationDto.getMessage());
        notEnt.setTimeStamp(LocalDateTime.now());
        return notEnt;
    }


}
