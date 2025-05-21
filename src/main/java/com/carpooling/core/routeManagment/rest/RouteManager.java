package com.carpooling.core.routeManagment.rest;

import com.carpooling.core.routeManagment.database.entities.RouteEntity;
import com.carpooling.core.routeManagment.database.entities.RoutePassengerEntity;
import com.carpooling.core.routeManagment.database.exceptions.NoRoutesFoundException;
import com.carpooling.core.routeManagment.database.exceptions.RouteNotInDbException;
import com.carpooling.core.routeManagment.database.exceptions.UserAlreadyInRouteExceotion;
import com.carpooling.core.routeManagment.database.repositories.RoutePassengerRepository;
import com.carpooling.core.routeManagment.database.repositories.RouteRepository;
import com.carpooling.core.routeManagment.rest.dtos.RouteDto;
import com.carpooling.core.routeManagment.rest.dtos.RoutePassengerDto;
import com.carpooling.core.userManagement.database.entities.UserEntity;
import com.carpooling.core.userManagement.database.exceptions.UserNotInDbException;
import com.carpooling.core.userManagement.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RouteManager {
    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RoutePassengerRepository routePassengerRepository;

    @Autowired
    private UserRepository userRepository;

    public RouteEntity addRoute(RouteDto routeDto) throws UserNotInDbException {
        return routeRepository.save(convertRouteDtoToRouteEntity(routeDto));
    }

    public RouteEntity addUserToRoute(RoutePassengerDto routePassengerDto) throws UserNotInDbException, RouteNotInDbException, UserAlreadyInRouteExceotion {
        RouteEntity routeEntity = routeRepository.findById(routePassengerDto.getRouteId()).orElseThrow(() -> new RouteNotInDbException("Route not found"));
        UserEntity userEntity = userRepository.findById(routePassengerDto.getUserId()).orElseThrow(() -> new UserNotInDbException("User not found"));

        RoutePassengerEntity passengerEntity = convertRoutePassengerDtoToRoutePassengerEntity(routePassengerDto);

        boolean alreadyAdded = routeEntity.getPassengers().stream()
                .anyMatch(p -> p.getUser().getId().equals(passengerEntity.getUser().getId()));

        if (!alreadyAdded) {
            routeEntity.getPassengers().add(convertRoutePassengerDtoToRoutePassengerEntity(routePassengerDto));
            routeEntity.setSeatsAvailable(routeEntity.getSeatsAvailable() - 1);
        }else{
            throw new UserAlreadyInRouteExceotion("User already in route");
        }
        return routeRepository.save(routeEntity);
    }

    public RouteEntity removeUserFromRoute(RoutePassengerDto routePassengerDto) throws RouteNotInDbException, UserNotInDbException {
        RouteEntity routeEntity = routeRepository.findById(routePassengerDto.getRouteId()).orElseThrow(() -> new RouteNotInDbException("Route not found"));
        UserEntity userEntity = userRepository.findById(routePassengerDto.getUserId()).orElseThrow(() -> new UserNotInDbException("User not found"));

        RoutePassengerEntity passengerEntity = routePassengerRepository.findByUserAndRoute(userEntity, routeEntity).orElseThrow(() -> new RouteNotInDbException("Route not found"));

        routeEntity.getPassengers().remove(passengerEntity);
        routeEntity.setSeatsAvailable(routeEntity.getSeatsAvailable() + 1);
        routePassengerRepository.delete(passengerEntity);

        return routeRepository.save(routeEntity);
    }

    public List<RouteEntity> getRoutesByUser(long userId) throws UserNotInDbException, NoRoutesFoundException {
        Optional<List<RouteEntity>> routeEntities = routeRepository.findByDriverOrPassenger(userRepository.findById(userId).orElseThrow(()
                -> new UserNotInDbException(String.format("User with ID %d not found", userId))));
        if(routeEntities.isEmpty()){
            throw new NoRoutesFoundException("No routes found");
        }
        return routeEntities.get();
    }

    public List<RouteEntity> getRoutesBySchool(long userId, String school) throws UserNotInDbException, NoRoutesFoundException {
        Optional<List<RouteEntity>> routeEntities = routeRepository.findByDriverSchool(school, userRepository.findById(userId).orElseThrow(()
                -> new UserNotInDbException(String.format("User with ID %d not found", userId))));
        if(routeEntities.isEmpty()){
            throw new NoRoutesFoundException("No routes found");
        }
        return routeEntities.get();
    }

    public List<RouteEntity> getRoutesBySchoolAndSearch(long userId, String school, String search) throws UserNotInDbException, NoRoutesFoundException {
        String searchTwo = "%" + search + "%";
        Optional<List<RouteEntity>> routeEntities = routeRepository.findByDriverSchoolAndSearch(school, searchTwo, userRepository.findById(userId).orElseThrow(()
                -> new UserNotInDbException(String.format("User with ID %d not found", userId))));

        if(routeEntities.isEmpty()){
            throw new NoRoutesFoundException("No routes found");
        }
        return routeEntities.get();
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

    private RoutePassengerEntity convertRoutePassengerDtoToRoutePassengerEntity(RoutePassengerDto routePassengerDto) throws UserNotInDbException, RouteNotInDbException {
        RoutePassengerEntity routePassengerEntity = new RoutePassengerEntity();
        routePassengerEntity.setUser(userRepository.findById(routePassengerDto.getUserId()).orElseThrow(() -> new UserNotInDbException(String.format("User with ID %d not found", routePassengerDto.getUserId()))));
        routePassengerEntity.setRoute(routeRepository.findById(routePassengerDto.getRouteId()).orElseThrow(() -> new RouteNotInDbException(String.format("Route with ID %d not found", routePassengerDto.getRouteId()))));
        routePassengerEntity.setNote(routePassengerDto.getNote());
        routePassengerEntity.setPickupLocation(routePassengerDto.getPickupLocation());
        return routePassengerEntity;
    }


}
