package com.carpooling.core.userManagement.rest;

import com.carpooling.core.userManagement.database.entities.UserEntity;
import com.carpooling.core.userManagement.database.exceptions.InvalidPasswordException;
import com.carpooling.core.userManagement.database.exceptions.UserAlreadyExistsException;
import com.carpooling.core.userManagement.database.exceptions.UserNotInDbException;
import com.carpooling.core.userManagement.database.repositories.UserRepository;
import com.carpooling.core.userManagement.rest.dtos.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserManager {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public UserEntity registerUser(UserDto userDto) throws UserAlreadyExistsException {
        if(userRepository.existsByEmail(userDto.getEmail()) || userRepository.existsByFullName(userDto.getFullname())) {
            throw new UserAlreadyExistsException(String.format("User with this email %s or fullname %s already exists", userDto.getEmail(), userDto.getFullname()));
        }
        UserEntity storedEntity = userRepository.save(convertUserDtoToUserEntity(userDto));
        return storedEntity;
    }

    public UserEntity loginUser(String email, String password) throws UserNotInDbException, InvalidPasswordException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null) {
            throw new UserNotInDbException(String.format("User with Email %s not found", email));
        }
        if(!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        return userEntity;
    }

    public UserEntity changePassword(long id, String oldPassword, String newPassword) throws UserNotInDbException, InvalidPasswordException {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotInDbException(String.format("User with ID %d not found", id)));
        if(!passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(userEntity);
    }

    private UserEntity convertUserDtoToUserEntity(@Valid UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(-1L);
        userEntity.setFullname(userDto.getFullname());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setSchool(userDto.getSchool());
        return userEntity;
    }
}
