package com.carpooling.core.routeManagment.restController;

import com.carpooling.core.routeManagment.rest.RouteDataService;
import com.carpooling.core.routeManagment.rest.dtos.RouteDto;
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

    @PostMapping(value = "/carpooling/route/add")
    public RouteResource addRoute(@Valid @RequestBody RouteDto routeDto) {
        System.out.println("DTO: " + routeDto);
        return routeDataService.addRoute(routeDto);
    }

    @GetMapping(value = "/carpooling/route/get/{userId}")
    public List<RouteResource> getRoutesByUser(@PathVariable long userId) {
        return routeDataService.getRoutesByUser(userId);
    }

    @GetMapping(value = "/carpooling/route/get/{userId}/{school}")
    public List<RouteResource> getRoutesBySchool(@PathVariable long userId, @PathVariable String school) {
        return routeDataService.getRoutesBySchool(userId, school);
    }

}
