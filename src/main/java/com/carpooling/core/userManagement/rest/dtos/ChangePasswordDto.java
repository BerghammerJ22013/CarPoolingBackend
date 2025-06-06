package com.carpooling.core.userManagement.rest.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangePasswordDto {
    @NotNull
    private String oldPassword;
    @NotNull
    private String newPassword;
}
