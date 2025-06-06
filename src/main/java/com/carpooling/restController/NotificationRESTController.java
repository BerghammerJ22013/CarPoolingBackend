package com.carpooling.restController;

import com.carpooling.core.notificationManagment.NotificationDataService;
import com.carpooling.core.notificationManagment.rest.dtos.NotificationDto;
import com.carpooling.core.notificationManagment.rest.resources.NotificationResource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/carpooling/notifications")
public class NotificationRESTController {
    @Autowired
    private NotificationDataService notificationDataService;

    @PostMapping
    public NotificationResource sendNotification(@Valid @RequestBody NotificationDto notificationDto){
        return notificationDataService.sendNotification(notificationDto);
    }

    @DeleteMapping(value = "/{notificationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeNotification(@PathVariable Long notificationId){
        notificationDataService.removeNotification(notificationId);
    }
}
