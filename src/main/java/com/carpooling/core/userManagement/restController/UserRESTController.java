package com.carpooling.core.userManagement.restController;

import com.carpooling.core.userManagement.data.LoginRequest;
import com.carpooling.core.userManagement.rest.UserDataService;
import com.carpooling.core.userManagement.rest.dtos.UserDto;
import com.carpooling.core.userManagement.rest.resources.UserResource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/carpooling")
public class UserRESTController {
    @Autowired
    private UserDataService userDataService;

    @RequestMapping(value = "/auth/register", method = RequestMethod.POST)
    public UserResource register(@Valid @RequestBody UserDto userDto) {
        return userDataService.registerUser(userDto);
    }

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public UserResource login(@Valid @RequestBody LoginRequest loginRequest) {
        return userDataService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @RequestMapping(value = "/user/{id}/password/{oldPassword}/{newPassword}", method = RequestMethod.PUT)
    public UserResource changePassword(@PathVariable long id, @PathVariable String oldPassword, @PathVariable String newPassword) {
        return userDataService.changePassword(id, oldPassword, newPassword);
    }

    @GetMapping(value = "/user/{fullName}")
    public UserResource getUserByFullName(@PathVariable String fullName) {
        return userDataService.getUserByFullName(fullName);
    }
}
