package com.carpooling.core.routeManagment.rest.dtos;

import com.carpooling.core.userManagement.database.entities.UserEntity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class RouteDto {
    @NotNull
    private String fromLocation;
    @NotNull
    private LocalDate date;
    @NotNull
    private LocalTime time;
    @Min(1)
    private int seatsAvailable;
    private List<String> stops;
    @NotNull
    private Long driverId;
}
