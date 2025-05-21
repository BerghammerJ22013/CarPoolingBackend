package com.carpooling.core.userManagement.data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @Email
    String email;
    @NotNull
    String password;
}
