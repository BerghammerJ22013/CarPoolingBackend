package com.carpooling.core.userManagement;

import com.carpooling.core.userManagement.database.entities.UserEntity;
import com.carpooling.core.userManagement.database.exceptions.InvalidPasswordException;
import com.carpooling.core.userManagement.database.exceptions.UserAlreadyExistsException;
import com.carpooling.core.userManagement.database.exceptions.UserNotInDbException;
import com.carpooling.core.userManagement.rest.dtos.UserDto;
import com.carpooling.core.userManagement.rest.exceptions.InvalidChangePasswordException;
import com.carpooling.core.userManagement.rest.exceptions.InvalidLoginException;
import com.carpooling.core.userManagement.rest.exceptions.InvalidRegisterException;
import com.carpooling.core.userManagement.rest.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDataService {
    @Autowired
    UserManager userManager;

    public UserResource registerUser(UserDto userDto) {
        try {
            return convertUserEntityToUserResource(userManager.registerUser(userDto));
        } catch (UserAlreadyExistsException e) {
            throw new InvalidRegisterException(e.getMessage());
        }
    }

    public UserResource loginUser(String email, String password) {
        try {
            return convertUserEntityToUserResource(userManager.loginUser(email, password));
        } catch (UserNotInDbException | InvalidPasswordException e) {
            throw new InvalidLoginException(e.getMessage());
        }
    }

    public UserResource changePassword(long id, String oldPassword, String newPassword) {
        try {
            return convertUserEntityToUserResource(userManager.changePassword(id, oldPassword, newPassword));
        } catch (UserNotInDbException | InvalidPasswordException e) {
            throw new InvalidChangePasswordException(e.getMessage());
        }
    }

    private UserResource convertUserEntityToUserResource(UserEntity userEntity) {
        UserResource userResource = new UserResource();
        userResource.setId(userEntity.getId());
        userResource.setFullName(userEntity.getFullName());
        userResource.setEmail(userEntity.getEmail());
        userResource.setSchool(userEntity.getSchool());
        return userResource;
    }


    public UserResource getUserByfullName(String fullName) {
        try {
            return convertUserEntityToUserResource(userManager.getUserByfullName(fullName));
        } catch (UserNotInDbException e) {
            throw new RuntimeException(e);
        }
    }
}
