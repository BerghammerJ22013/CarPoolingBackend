package com.carpooling.core.routeManagment.rest.resources;

import lombok.Data;

@Data
public class RoutePassengerResource {
    private Long routeId;
    private String fullName;
    private String pickupLocation;
    private String note;
}
