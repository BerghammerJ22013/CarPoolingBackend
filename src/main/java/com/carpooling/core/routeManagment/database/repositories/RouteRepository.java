package com.carpooling.core.routeManagment.database.repositories;

import com.carpooling.core.routeManagment.database.entities.RouteEntity;
import com.carpooling.core.userManagement.database.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RouteRepository extends CrudRepository<RouteEntity, Long> {

    @Query("SELECT r FROM RouteEntity r WHERE r.driver = ?1 OR ?1 MEMBER OF r.passengers")
    List<RouteEntity> findByDriverOrPassenger(UserEntity user);

    @Query("SELECT r FROM RouteEntity r WHERE r.driver.school = :school AND r.driver != :user AND :user NOT MEMBER OF r.passengers AND r.seatsAvailable > 0")
    List<RouteEntity> findByDriverSchool(@Param("school") String school, @Param("user") UserEntity user);

    @Query("SELECT r FROM RouteEntity r " +
            "WHERE r.driver.school = :school " +
            "AND r.driver != :user " +
            "AND :user NOT MEMBER OF r.passengers " +
            "AND r.seatsAvailable > 0 " +
            "AND (r.fromLocation LIKE :search OR r.driver.fullname LIKE :search)")
    List<RouteEntity> findByDriverSchoolAndSearch(@Param("school") String school, @Param("search") String search, @Param("user") UserEntity user);

}
