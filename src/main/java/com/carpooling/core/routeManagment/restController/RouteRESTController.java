package com.carpooling.core.routeManagment.restController;

import com.carpooling.core.routeManagment.rest.RouteDataService;
import com.carpooling.core.routeManagment.rest.dtos.RouteDto;
import com.carpooling.core.routeManagment.rest.dtos.RoutePassengerDto;
import com.carpooling.core.routeManagment.rest.resources.RouteResource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class RouteRESTController {
    @Autowired
    private RouteDataService routeDataService;

    @PostMapping(value = "/carpooling/route")
    public RouteResource addRoute(@Valid @RequestBody RouteDto routeDto) {
        return routeDataService.addRoute(routeDto);
    }

    @GetMapping(value = "/carpooling/route/{userId}")
    public List<RouteResource> getRoutesByUser(@PathVariable long userId) {
        return routeDataService.getRoutesByUser(userId);
    }

    @GetMapping(value = "/carpooling/route/{userId}/{school}")
    public List<RouteResource> getRoutesBySchool(@PathVariable long userId, @PathVariable String school) {
        return routeDataService.getRoutesBySchool(userId, school);
    }

    @GetMapping(value = "/carpooling/route/{userId}/{school}/search/{search}")
    public List<RouteResource> getRoutesBySchoolAndSearch(@PathVariable long userId, @PathVariable String school, @PathVariable String search) {
        return routeDataService.getRoutesBySchoolAndSearch(userId, school, search);
    }

    @PutMapping(value = "/carpooling/route/user")
    public RouteResource addUserToRoute(@Valid @RequestBody RoutePassengerDto routePassengerDto) {
        return routeDataService.addUserToRoute(routePassengerDto);
    }

    @DeleteMapping(value = "/carpooling/route/user")
    public RouteResource removeUserFromRoute(@Valid @RequestBody RoutePassengerDto routePassengerDto) {
        return routeDataService.removeUserFromRoute(routePassengerDto);
    }

    @DeleteMapping(value = "/carpooling/route/{routeId}/passenger/{fullName}")
    public RouteResource removePassengerFromRoute(@PathVariable long routeId, @PathVariable String fullName) {
        return routeDataService.removePassengerFromRoute(routeId, fullName);
    }
}