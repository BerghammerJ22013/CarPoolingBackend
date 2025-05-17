package com.carpooling.core.userManagement.data;

import lombok.Data;

@Data
public class LoginRequest {
    String email;
    String password;
}
