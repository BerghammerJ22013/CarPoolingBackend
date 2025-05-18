package com.carpooling.core.routeManagment.rest;

import com.carpooling.core.routeManagment.database.entities.RouteEntity;
import com.carpooling.core.routeManagment.database.repositories.RouteRepository;
import com.carpooling.core.routeManagment.rest.dtos.RouteDto;
import com.carpooling.core.userManagement.database.exceptions.UserNotInDbException;
import com.carpooling.core.userManagement.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RouteManager {
    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private UserRepository userRepository;

    public RouteEntity addRoute(RouteDto routeDto) throws UserNotInDbException {
        return routeRepository.save(convertRouteDtoToRouteEntity(routeDto));
    }

    public List<RouteEntity> getRoutesByUser(long userId) throws UserNotInDbException {
        List<RouteEntity> routeEntities = new ArrayList<>();
        routeEntities = routeRepository.findByDriverOrPassenger(userRepository.findById(userId).orElseThrow(()
                -> new UserNotInDbException(String.format("User with ID %d not found", userId))));
        return routeEntities;

    }

    public List<RouteEntity> getRoutesBySchool(long userId, String school) throws UserNotInDbException {
        List<RouteEntity> routeEntities = new ArrayList<>();
        routeEntities = routeRepository.findByDriverSchool(school, userRepository.findById(userId).orElseThrow(()
                -> new UserNotInDbException(String.format("User with ID %d not found", userId))));
        return routeEntities;
    }

    public List<RouteEntity> getRoutesBySchoolAndSearch(long userId, String school, String search) throws UserNotInDbException {
        List<RouteEntity> routeEntities = new ArrayList<>();
        String searchTwo = "%" + search + "%";
        routeEntities = routeRepository.findByDriverSchoolAndSearch(school, searchTwo, userRepository.findById(userId).orElseThrow(()
                -> new UserNotInDbException(String.format("User with ID %d not found", userId))));

        String finalSearch = search;
        List<RouteEntity> all = (List<RouteEntity>) routeRepository.findAll();
        List<RouteEntity> filteredRouteEntities = all.stream()
                .filter(r -> r.getStops().stream().anyMatch(s -> s.contains(finalSearch)))
                .toList();
        return filteredRouteEntities;
    }

    private RouteEntity convertRouteDtoToRouteEntity(RouteDto routeDto) throws UserNotInDbException {
        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setDate(routeDto.getDate());
        routeEntity.setStops(routeDto.getStops());
        routeEntity.setDriver(userRepository.findById(routeDto.getDriverId()).orElseThrow(() -> new UserNotInDbException(String.format("User with ID %d not found", routeDto.getDriverId()))));
        routeEntity.setFromLocation(routeDto.getFromLocation());
        routeEntity.setSeatsAvailable(routeDto.getSeatsAvailable());
        routeEntity.setTime(routeDto.getTime());
        return routeEntity;
    }


}
