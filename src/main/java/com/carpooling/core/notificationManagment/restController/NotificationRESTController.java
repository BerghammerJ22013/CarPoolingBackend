package com.carpooling.core.notificationManagment.restController;

import com.carpooling.core.notificationManagment.rest.NotificationDataService;
import com.carpooling.core.notificationManagment.rest.dtos.NotificationDto;
import com.carpooling.core.notificationManagment.rest.resources.NotificationResource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
//@RequestMapping(value = "/carpooling/notification")
public class NotificationRESTController {
    @Autowired
    private NotificationDataService notificationDataService;

    @GetMapping(value = "/carpooling/notification/{userId}")
    public List<NotificationResource> getNotificationsByReceiver(@PathVariable Long userId){
        return notificationDataService.getNotificationsByReceiver(userId);
    }

    @GetMapping(value = "/carpooling/notification/{userId}/today")
    public List<NotificationResource> getNotificationsByReceiverOfToday(@PathVariable Long userId){
        return notificationDataService.getNotificationsByReceiverOfToday(userId);
    }

    @PostMapping(value = "/carpooling/notification")
    public NotificationResource sendNotification(@Valid @RequestBody NotificationDto notificationDto){
        return notificationDataService.sendNotification(notificationDto);
    }

    @DeleteMapping(value = "/carpooling/notification/{notificationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeNotification(@PathVariable Long notificationId){
        notificationDataService.removeNotification(notificationId);
    }
}
