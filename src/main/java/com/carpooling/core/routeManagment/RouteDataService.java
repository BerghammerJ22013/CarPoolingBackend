package com.carpooling.core.routeManagment;

import com.carpooling.core.routeManagment.database.entities.RouteEntity;
import com.carpooling.core.routeManagment.database.entities.RoutePassengerEntity;
import com.carpooling.core.routeManagment.database.exceptions.NoRoutesFoundException;
import com.carpooling.core.routeManagment.database.exceptions.RouteNotInDbException;
import com.carpooling.core.routeManagment.database.exceptions.UserAlreadyInRouteExceotion;
import com.carpooling.core.routeManagment.rest.dtos.RouteDto;
import com.carpooling.core.routeManagment.rest.dtos.RoutePassengerDto;
import com.carpooling.core.routeManagment.rest.exceptions.*;
import com.carpooling.core.routeManagment.rest.resources.RoutePassengerResource;
import com.carpooling.core.routeManagment.rest.resources.RouteResource;
import com.carpooling.core.userManagement.database.exceptions.UserNotInDbException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class RouteDataService {
    @Autowired
    private RouteManager routeManager;

    public RouteResource addRoute(RouteDto routeDto) {
        try {
            return convertRouteEntityToRouteResource(routeManager.addRoute(routeDto));
        } catch (UserNotInDbException e) {
            throw new InvalidAddRouteException(e.getMessage());
        }
    }

    public RouteResource addPassengerToRoute(RoutePassengerDto routePassengerDto) {
        try {
            return convertRouteEntityToRouteResource(routeManager.addPassengerToRoute(routePassengerDto));
        } catch (UserNotInDbException | RouteNotInDbException | UserAlreadyInRouteExceotion e) {
            throw new InvalidAddUserToRouteException(e.getMessage());
        }
    }

    public RouteResource removePassengerFromRoute(RoutePassengerDto routePassengerDto) {
        try{
            return convertRouteEntityToRouteResource(routeManager.removePassengerFromRoute(routePassengerDto));
        } catch (UserNotInDbException | RouteNotInDbException e) {
            throw new InvalidRemovalOfUserException(e.getMessage());
        }
    }

    public RouteResource kickPassengerFromRoute(Long routeId, String fullName) {
        try {
            return convertRouteEntityToRouteResource(routeManager.kickPassengerFromRoute(routeId, fullName));
        } catch (RouteNotInDbException | UserNotInDbException e) {
            throw new InvalidRemovalOfUserException(e.getMessage());
        }
    }

    public void removeRoute(Long routeId) {
        try {
            routeManager.removeRoute(routeId);
        } catch (RouteNotInDbException | UserNotInDbException e) {
            throw new InvalidRouteRemovalException(e.getMessage());
        }
    }

    public List<RouteResource> getRoutesByUser(Long userId) {
        List<RouteEntity> routeEntities = null;
        try {
            routeEntities = routeManager.getRoutesByUser(userId);
            List<RouteResource> routeResources = new ArrayList<>();
            for (RouteEntity routeEntity : routeEntities) {
                routeResources.add(convertRouteEntityToRouteResource(routeEntity));
            }
            if (routeResources.isEmpty()) {
                throw new UserHasNoRoutesException(String.format("User with ID %d has no routes", userId));
            }
            return routeResources;
        } catch (UserNotInDbException | NoRoutesFoundException e) {
            throw new InvalidGetRoutesByUserException(e.getMessage());
        }
    }

    public List<RouteResource> getRoutesBySchool(Long userId, String school) {
        List<RouteEntity> routeEntities = null;
        try {
            routeEntities = routeManager.getRoutesBySchool(userId, school);
            List<RouteResource> routeResources = new ArrayList<>();
            for (RouteEntity routeEntity : routeEntities) {
                routeResources.add(convertRouteEntityToRouteResource(routeEntity));
            }
            if (routeResources.isEmpty()) {
                throw new InvalidGetRoutesBySchoolException(String.format("User with ID %d has no routes", userId));
            }
            return routeResources;
        } catch (UserNotInDbException | NoRoutesFoundException e) {
            throw new InvalidGetRoutesBySchoolException(e.getMessage());
        }
    }

    public List<RouteResource> getRoutesBySchoolAndSearch(Long userId, String school, String search) {
        List<RouteEntity> routeEntities = null;
        try {
            routeEntities = routeManager.getRoutesBySchoolAndSearch(userId, school, search);
            List<RouteResource> routeResources = new ArrayList<>();
            for (RouteEntity routeEntity : routeEntities) {
                routeResources.add(convertRouteEntityToRouteResource(routeEntity));
            }
            if (routeResources.isEmpty()) {
                throw new InvalidGetRoutesBySchoolAndSearchException(String.format("User with ID %d has no routes", userId));
            }
            return routeResources;
        } catch (UserNotInDbException | NoRoutesFoundException  e) {
            throw new InvalidGetRoutesBySchoolAndSearchException(e.getMessage());
        }
    }

    public RouteResource convertRouteEntityToRouteResource(RouteEntity routeEntity){
        RouteResource routeResource = new RouteResource();
        routeResource.setId(routeEntity.getId());
        routeResource.setDate(routeEntity.getDate());
        routeResource.setStops(routeEntity.getStops());
        routeResource.setDriverName(routeEntity.getDriver().getFullName());
        routeResource.setFromLocation(routeEntity.getFromLocation());
        routeResource.setSeatsAvailable(routeEntity.getSeatsAvailable());
        routeResource.setTime(routeEntity.getTime());
        List<RoutePassengerResource> passengers = new ArrayList<>();
        if(routeEntity.getPassengers() == null) {
            routeResource.setPassengers(passengers);
            return routeResource;
        }

        for (RoutePassengerEntity routePassengerEntity : routeEntity.getPassengers()) {
            passengers.add(convertRoutePassengerEntityToRoutePassengerResource(routePassengerEntity));
        }

        routeResource.setPassengers(passengers);
        return routeResource;
    }

    public RoutePassengerResource convertRoutePassengerEntityToRoutePassengerResource(RoutePassengerEntity routePassengerEntity){
        RoutePassengerResource routePassengerResource = new RoutePassengerResource();
        routePassengerResource.setNote(routePassengerEntity.getNote());
        routePassengerResource.setPickupLocation(routePassengerEntity.getPickupLocation());
        routePassengerResource.setRouteId(routePassengerEntity.getRoute().getId());
        routePassengerResource.setFullName(routePassengerEntity.getUser().getFullName());
        return  routePassengerResource;
    }



}
