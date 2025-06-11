package com.carpooling.core.userManagement;

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

import java.util.Optional;

@Service
public class UserManager {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserEntity getUserById(long id)
            throws UserNotInDbException {
        return userRepository.findById(id)
                .orElseThrow(()
                        -> new UserNotInDbException(String.format("User with ID %d not found", id)));
    }

    public UserEntity getUserByfullName(String fullName)
            throws UserNotInDbException {
        return userRepository.findByfullName(fullName)
                .orElseThrow(()
                        -> new UserNotInDbException(String.format("User with fullName %s not found", fullName)));
    }

    public UserEntity registerUser(UserDto userDto)
            throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistsException(String.format("User with this email %s or fullName %s already exists",
                    userDto.getEmail(),
                    userDto.getFullName()
            ));
        }
        return userRepository.save(convertUserDtoToUserEntity(userDto));
    }

    public UserEntity loginUser(String email, String password)
            throws UserNotInDbException, InvalidPasswordException {
        Optional<UserEntity> oUserEntity = userRepository.findByEmail(email);
        if (oUserEntity.isEmpty()) {
            throw new UserNotInDbException(String.format("User with Email %s not found", email));
        }

        UserEntity userEntity = oUserEntity.get();

        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        return userEntity;
    }

    public UserEntity changePassword(long id, String oldPassword, String newPassword)
            throws UserNotInDbException, InvalidPasswordException {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(()
                        -> new UserNotInDbException(String.format("User with ID %d not found", id)));
        if (!passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(userEntity);
    }

    private UserEntity convertUserDtoToUserEntity(@Valid UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFullName(userDto.getFullName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setSchool(userDto.getSchool());
        return userEntity;
    }
}
