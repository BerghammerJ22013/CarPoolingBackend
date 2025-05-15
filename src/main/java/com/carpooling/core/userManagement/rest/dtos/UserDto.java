package com.carpooling.core.userManagement.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    @NotEmpty
    private String fullname;

    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String school;
}
