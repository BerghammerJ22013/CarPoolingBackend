package com.carpooling.core.routeManagment.database.repositories;

import com.carpooling.core.routeManagment.database.entities.RouteEntity;
import com.carpooling.core.userManagement.database.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RouteRepository extends CrudRepository<RouteEntity, Long> {
    List<RouteEntity> findByDriver(UserEntity driver);
    List<RouteEntity> findByPassengersContaining(UserEntity passenger);

    @Query("SELECT r FROM RouteEntity r WHERE r.driver = ?1 OR ?1 MEMBER OF r.passengers")
    List<RouteEntity> findByDriverOrPassenger(UserEntity user);

    @Query("SELECT r FROM RouteEntity r WHERE EXISTS(SELECT d FROM UserEntity d WHERE d.school = ?1) AND r.driver != ?2")
    List<RouteEntity> findByDriverSchool(String school, UserEntity user);

}
