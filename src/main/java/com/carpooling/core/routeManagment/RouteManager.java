package com.carpooling.core.routeManagment;

import com.carpooling.core.notificationManagment.NotificationManager;
import com.carpooling.core.notificationManagment.rest.dtos.NotificationDto;
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
import com.carpooling.core.userManagement.UserManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class RouteManager {
    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RoutePassengerRepository routePassengerRepository;

    @Autowired
    private UserManager userManager;

    @Autowired
    private NotificationManager notificationManager;

    public RouteEntity addRoute(RouteDto routeDto)
            throws UserNotInDbException {
        return routeRepository.save(convertRouteDtoToRouteEntity(routeDto));
    }

    public RouteEntity addPassengerToRoute(RoutePassengerDto routePassengerDto)
            throws UserNotInDbException, RouteNotInDbException, UserAlreadyInRouteExceotion {
        RouteEntity routeEntity = routeRepository.findById(routePassengerDto.getRouteId())
                .orElseThrow(()
                -> new RouteNotInDbException("Route not found"));
        UserEntity userEntity = userManager.getUserById(routePassengerDto.getUserId());

        RoutePassengerEntity passengerEntity = convertRoutePassengerDtoToRoutePassengerEntity(routePassengerDto);

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setReceiverId(routeEntity.getDriver().getId());
        notificationDto.setMessage(String.format(
                "Hat sich für deine Fahrt am %s um %s ab %s angemeldet.",
                routeEntity.getDate().format(DateTimeFormatter.ofPattern("dd.MMMM.yyyy")),
                routeEntity.getTime(),
                routePassengerDto.getPickupLocation()
        ));
        notificationDto.setSenderId(userEntity.getId());

        notificationManager.sendNotification(notificationDto);

        boolean alreadyAdded = routeEntity.getPassengers().stream()
                .anyMatch(p -> p.getUser()
                        .getId()
                        .equals(passengerEntity.getUser().getId()));

        if (alreadyAdded) {
            throw new UserAlreadyInRouteExceotion("User already in route");
        }

        routeEntity.getPassengers().add(convertRoutePassengerDtoToRoutePassengerEntity(routePassengerDto));
        routeEntity.setSeatsAvailable(routeEntity.getSeatsAvailable() - 1);
        
        return routeRepository.save(routeEntity);
    }

    public RouteEntity removePassengerFromRoute(RoutePassengerDto routePassengerDto)
            throws RouteNotInDbException, UserNotInDbException {
        RouteEntity routeEntity = routeRepository.findById(routePassengerDto.getRouteId())
                .orElseThrow(()
                -> new RouteNotInDbException("Route not found"));
        UserEntity userEntity = userManager.getUserById(routePassengerDto.getUserId());

        RoutePassengerEntity passengerEntity = routePassengerRepository
                .findByUserAndRoute(userEntity, routeEntity)
                .orElseThrow(() -> new RouteNotInDbException("Route not found"));

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setReceiverId(routeEntity.getDriver().getId());
        notificationDto.setMessage(String.format(
                "Hat sich für deine Fahrt am %s um %s abgemeldet.",
                routeEntity.getDate().format(DateTimeFormatter.ofPattern("dd.MMMM.yyyy")),
                routeEntity.getTime()
        ));
        notificationDto.setSenderId(userEntity.getId());

        notificationManager.sendNotification(notificationDto);

        routeEntity.getPassengers().remove(passengerEntity);
        routeEntity.setSeatsAvailable(routeEntity.getSeatsAvailable() + 1);
        routePassengerRepository.delete(passengerEntity);

        return routeRepository.save(routeEntity);
    }

    public RouteEntity kickPassengerFromRoute(Long routeId, String fullName)
            throws RouteNotInDbException, UserNotInDbException {
        RouteEntity routeEntity = routeRepository.findById(routeId).orElseThrow(()
                -> new RouteNotInDbException("Route not found"));
        UserEntity userEntity = userManager.getUserByfullName(fullName);

        RoutePassengerEntity passengerEntity = routePassengerRepository
                .findByUserAndRoute(userEntity, routeEntity)
                .orElseThrow(()
                -> new RouteNotInDbException("Passenger not found"));

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setSenderId(routeEntity.getDriver().getId());
        notificationDto.setMessage("Du wurdest aus der Fahrt entfernt.");
        notificationDto.setReceiverId(userEntity.getId());

        notificationManager.sendNotification(notificationDto);

        routeEntity.getPassengers().remove(passengerEntity);
        routeEntity.setSeatsAvailable(routeEntity.getSeatsAvailable() + 1);
        routePassengerRepository.delete(passengerEntity);

        return routeRepository.save(routeEntity);
    }

    public void removeRoute(Long routeId)
            throws RouteNotInDbException, UserNotInDbException {
        RouteEntity routeEntity = routeRepository.findById(routeId).orElseThrow(()
                -> new RouteNotInDbException("Route not found"));

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setMessage(String.format(
                "Die Route am %s um %s wurde gelöscht.",
                routeEntity.getDate().format(DateTimeFormatter.ofPattern("dd.MMMM.yyyy")),
                routeEntity.getTime()
        ));
        notificationDto.setSenderId(routeEntity.getDriver().getId());

        for(RoutePassengerEntity rpe : routeEntity.getPassengers()){
            notificationDto.setReceiverId(rpe.getUser().getId());
            notificationManager.sendNotification(notificationDto);
        }

        routePassengerRepository.deleteAll(routeEntity.getPassengers());
        routeRepository.delete(routeEntity);
    }

    public List<RouteEntity> getRoutesByUser(Long userId)
            throws UserNotInDbException, NoRoutesFoundException {
        Optional<List<RouteEntity>> routeEntities = routeRepository
                .findByDriverOrPassenger(userManager.getUserById(userId));
        
        if(routeEntities.isEmpty()){
            throw new NoRoutesFoundException("No routes found");
        }
        
        return routeEntities.get();
    }

    public List<RouteEntity> getRoutesBySchool(Long userId, String school)
            throws UserNotInDbException, NoRoutesFoundException {
        Optional<List<RouteEntity>> routeEntities = routeRepository
                .findByDriverSchool(school, userManager.getUserById(userId));
        if(routeEntities.isEmpty()){
            throw new NoRoutesFoundException("No routes found");
        }
        return routeEntities.get();
    }

    public List<RouteEntity> getRoutesBySchoolAndSearch(Long userId, String school, String search)
            throws UserNotInDbException, NoRoutesFoundException {
        String searchTwo = "%" + search + "%";
        Optional<List<RouteEntity>> routeEntities = routeRepository
                .findByDriverSchoolAndSearch(school, searchTwo, userManager.getUserById(userId));

        if(routeEntities.isEmpty()){
            throw new NoRoutesFoundException("No routes found");
        }
        return routeEntities.get();
    }

    private RouteEntity convertRouteDtoToRouteEntity(RouteDto routeDto)
            throws UserNotInDbException {
        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setDate(routeDto.getDate());
        routeEntity.setStops(routeDto.getStops());
        routeEntity.setDriver(userManager.getUserById(routeDto.getDriverId()));
        routeEntity.setFromLocation(routeDto.getFromLocation());
        routeEntity.setSeatsAvailable(routeDto.getSeatsAvailable());
        routeEntity.setTime(routeDto.getTime());
        return routeEntity;
    }

    private RoutePassengerEntity convertRoutePassengerDtoToRoutePassengerEntity(RoutePassengerDto routePassengerDto)
            throws UserNotInDbException, RouteNotInDbException {
        RoutePassengerEntity routePassengerEntity = new RoutePassengerEntity();
        routePassengerEntity.setUser(userManager.getUserById(routePassengerDto.getUserId()));
        routePassengerEntity.setRoute(routeRepository.findById(routePassengerDto.getRouteId())
                .orElseThrow(()
                -> new RouteNotInDbException(String.format("Route with ID %d not found", routePassengerDto.getRouteId()))));
        routePassengerEntity.setNote(routePassengerDto.getNote());
        routePassengerEntity.setPickupLocation(routePassengerDto.getPickupLocation());
        return routePassengerEntity;
    }



}
