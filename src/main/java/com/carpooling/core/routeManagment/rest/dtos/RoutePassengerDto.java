package com.carpooling.core.routeManagment.rest.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoutePassengerDto {
    @NotNull
    private Long routeId;
    @NotNull
    private Long userId;
    @NotNull
    private String pickupLocation;
    private String note;
}
