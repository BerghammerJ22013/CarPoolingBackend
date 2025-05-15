package com.carpooling.core.userManagement.rest;

import com.carpooling.core.userManagement.database.entities.UserEntity;
import com.carpooling.core.userManagement.database.repositories.UserRepository;
import com.carpooling.core.userManagement.rest.dtos.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManager {
    @Autowired
    private UserRepository userRepository;



    public UserEntity registerUser(UserDto userDto) {
        userRepository.save(convertUserDtoToUserEntity(userDto));
        return null;
    }

    private UserEntity convertUserDtoToUserEntity(@Valid UserDto userDto) {
        return null;
    }
}
