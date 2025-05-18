package com.carpooling.core.routeManagment.rest;

import com.carpooling.core.routeManagment.database.entities.RouteEntity;
import com.carpooling.core.routeManagment.database.exceptions.RouteNotInDbException;
import com.carpooling.core.routeManagment.database.exceptions.UserAlreadyInRouteExceotion;
import com.carpooling.core.routeManagment.rest.dtos.RouteDto;
import com.carpooling.core.routeManagment.rest.exceptions.*;
import com.carpooling.core.routeManagment.rest.resources.RouteResource;
import com.carpooling.core.userManagement.database.entities.UserEntity;
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

    public RouteResource addUserToRoute(long routeId, long userId) {
        try {
            return convertRouteEntityToRouteResource(routeManager.addUserToRoute(routeId, userId));
        } catch (UserNotInDbException | RouteNotInDbException | UserAlreadyInRouteExceotion e) {
            throw new InvalidAddUserToRouteException(e.getMessage());
        }
    }

    public List<RouteResource> getRoutesByUser(long userId) {
        List<RouteEntity> routeEntities = null;
        try {
            routeEntities = routeManager.getRoutesByUser(userId);
            List<RouteResource> routeResources = new ArrayList<>();
            for (RouteEntity routeEntity : routeEntities) {
                routeResources.add(convertRouteEntityToRouteResource(routeEntity));
            }
            if (routeResources.isEmpty()) {
                throw new InvalidGetRoutesByUserException(String.format("User with ID %d has no routes", userId));
            }
            return routeResources;
        } catch (UserNotInDbException e) {
            throw new InvalidGetRoutesByUserException(e.getMessage());
        }
    }

    public List<RouteResource> getRoutesBySchool(long userId, String school) {
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
        } catch (UserNotInDbException e) {
            throw new InvalidGetRoutesBySchoolException(e.getMessage());
        }
    }

    public List<RouteResource> getRoutesBySchoolAndSearch(long userId, String school, String search) {
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
        } catch (UserNotInDbException e) {
            throw new InvalidGetRoutesBySchoolAndSearchException(e.getMessage());
        }
    }

    public RouteResource convertRouteEntityToRouteResource(RouteEntity routeEntity){
        RouteResource routeResource = new RouteResource();
        routeResource.setId(routeEntity.getId());
        routeResource.setDate(routeEntity.getDate());
        routeResource.setStops(routeEntity.getStops());
        routeResource.setDriverName(routeEntity.getDriver().getFullname());
        routeResource.setFromLocation(routeEntity.getFromLocation());
        routeResource.setSeatsAvailable(routeEntity.getSeatsAvailable());
        routeResource.setTime(routeEntity.getTime());
        List<Long> passengersIds = new ArrayList<>();
        if(routeEntity.getPassengers() == null) {
            routeResource.setPassengersIds(passengersIds);
            return routeResource;
        }
        for (UserEntity passenger : routeEntity.getPassengers()) {
            passengersIds.add(passenger.getId());
        }
        routeResource.setPassengersIds(passengersIds);
        return routeResource;
    }



}
