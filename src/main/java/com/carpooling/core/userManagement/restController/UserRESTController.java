package com.carpooling.core.userManagement.restController;

import com.carpooling.core.userManagement.rest.UserDataService;
import com.carpooling.core.userManagement.rest.dtos.UserDto;
import com.carpooling.core.userManagement.rest.resources.UserResource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserRESTController {
    @Autowired
    private UserDataService userDataService;

    @RequestMapping(value = "/carpooling/auth/register", method = RequestMethod.POST)
    public UserResource login(@Valid @RequestBody UserDto userDto) {
        return userDataService.registerUser(userDto);
    }
}
