package com.carpooling.restController;

import com.carpooling.core.routeManagment.RouteDataService;
import com.carpooling.core.routeManagment.rest.dtos.RouteDto;
import com.carpooling.core.routeManagment.rest.dtos.RoutePassengerDto;
import com.carpooling.core.routeManagment.rest.resources.RouteResource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/carpooling/routes")
public class RouteRESTController {
    @Autowired
    private RouteDataService routeDataService;

    @PostMapping
    public RouteResource addRoute(@Valid @RequestBody RouteDto routeDto) {
        return routeDataService.addRoute(routeDto);
    }

    @PutMapping(value = "/passengers")
    public RouteResource addPassengerToRoute(@Valid @RequestBody RoutePassengerDto routePassengerDto) {
        return routeDataService.addPassengerToRoute(routePassengerDto);
    }

    @DeleteMapping(value = "/passengers")
    public RouteResource removePassengerFromRoute(@Valid @RequestBody RoutePassengerDto routePassengerDto) {
        return routeDataService.removePassengerFromRoute(routePassengerDto);
    }

    @DeleteMapping(value = "/{routeId}/passengers/{fullName}")
    public RouteResource kickPassengerFromRoute(@PathVariable Long routeId, @PathVariable String fullName) {
        return routeDataService.kickPassengerFromRoute(routeId, fullName);
    }

    @DeleteMapping(value = "/{routeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeRoute(@PathVariable Long routeId) {
         routeDataService.removeRoute(routeId);
    }
}