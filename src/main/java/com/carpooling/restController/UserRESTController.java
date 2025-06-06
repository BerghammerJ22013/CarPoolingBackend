package com.carpooling.restController;

import com.carpooling.core.notificationManagment.NotificationDataService;
import com.carpooling.core.notificationManagment.rest.resources.NotificationResource;
import com.carpooling.core.routeManagment.RouteDataService;
import com.carpooling.core.routeManagment.rest.resources.RouteResource;
import com.carpooling.core.userManagement.rest.dtos.ChangePasswordDto;
import com.carpooling.core.userManagement.rest.dtos.LoginDto;
import com.carpooling.core.userManagement.UserDataService;
import com.carpooling.core.userManagement.rest.dtos.UserDto;
import com.carpooling.core.userManagement.rest.resources.UserResource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/carpooling/users")
public class UserRESTController {
    @Autowired
    private UserDataService userDataService;

    @Autowired
    private RouteDataService routeDataService;
    
    @Autowired
    private NotificationDataService notificationDataService;

    @GetMapping(value = "/{userId}/notifications")
    public List<NotificationResource> getNotificationsByReceiver(@PathVariable Long userId){
        return notificationDataService.getNotificationsByReceiver(userId);
    }

    @GetMapping(value = "/{userId}/notifications/today")
    public List<NotificationResource> getNotificationsByReceiverOfToday(@PathVariable Long userId){
        return notificationDataService.getNotificationsByReceiverOfToday(userId);
    }

    @GetMapping(value = "/{userId}/routes")
    public List<RouteResource> getRoutesByUser(@PathVariable Long userId) {
        return routeDataService.getRoutesByUser(userId);
    }

    @GetMapping(value = "/{userId}/routes/schools/{school}")
    public List<RouteResource> getRoutesBySchool(@PathVariable Long userId, @PathVariable String school) {
        return routeDataService.getRoutesBySchool(userId, school);
    }

    @GetMapping(value = "/{userId}/routes/schools/{school}/search")
    public List<RouteResource> getRoutesBySchoolAndSearch(@PathVariable Long userId, @PathVariable String school, @RequestParam String query) {
        return routeDataService.getRoutesBySchoolAndSearch(userId, school, query);
    }

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public UserResource register(@Valid @RequestBody UserDto userDto) {
        return userDataService.registerUser(userDto);
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public UserResource login(@Valid @RequestBody LoginDto loginDto) {
        return userDataService.loginUser(loginDto.getEmail(), loginDto.getPassword());
    }

    @RequestMapping(value = "/{userId}/password", method = RequestMethod.PUT)
    public UserResource changePassword(@PathVariable long userId,@Valid @RequestBody ChangePasswordDto changePasswordDto) {
        return userDataService.changePassword(userId, changePasswordDto.getOldPassword(), changePasswordDto.getNewPassword());
    }

    @GetMapping(value = "/{fullName}")
    public UserResource getUserByfullName(@PathVariable String fullName) {
        return userDataService.getUserByfullName(fullName);
    }
}
