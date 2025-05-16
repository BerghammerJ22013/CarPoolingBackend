package com.carpooling.core.routeManagment.rest.dtos;

import com.carpooling.core.userManagement.database.entities.UserEntity;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class RouteDto {
    private String fromLocation;
    private LocalDate date;
    private LocalTime time;
    private int seatsAvailable;
    private List<String> stops;
    private Long driverId;
}
