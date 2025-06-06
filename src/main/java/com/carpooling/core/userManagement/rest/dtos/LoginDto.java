package com.carpooling.core.userManagement.rest.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDto {
    @Email
    String email;
    @NotNull
    String password;
}
