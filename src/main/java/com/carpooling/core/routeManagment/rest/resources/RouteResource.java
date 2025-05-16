package com.carpooling.core.routeManagment.rest.resources;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class RouteResource {
    private Long id;
    private String fromLocation;
    private LocalDate date;
    private LocalTime time;
    private int seatsAvailable;
    private List<String> stops;
    private Long driverId;
}
