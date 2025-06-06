package com.carpooling.core.routeManagment.database.repositories;

import com.carpooling.core.routeManagment.database.entities.RouteEntity;
import com.carpooling.core.userManagement.database.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends CrudRepository<RouteEntity, Long> {

    @Query("SELECT r FROM RouteEntity r WHERE r.driver = ?1 OR EXISTS (SELECT rp FROM RoutePassengerEntity rp WHERE rp.route = r AND rp.user = ?1)")
    Optional<List<RouteEntity>> findByDriverOrPassenger(UserEntity user);

    @Query("SELECT r FROM RouteEntity r WHERE r.driver.school = :school AND r.driver != :user AND NOT EXISTS (SELECT rp FROM RoutePassengerEntity rp WHERE rp.route = r AND rp.user = :user) AND r.seatsAvailable > 0")
    Optional<List<RouteEntity>> findByDriverSchool(@Param("school") String school, @Param("user") UserEntity user);

    @Query("""
    SELECT r FROM RouteEntity r 
    WHERE r.driver.school = :school 
      AND r.driver != :user 
      AND NOT EXISTS (
          SELECT rp FROM RoutePassengerEntity rp 
          WHERE rp.route = r AND rp.user = :user
      ) 
      AND r.seatsAvailable > 0 
      AND (
          LOWER(r.fromLocation) LIKE LOWER(:search) 
          OR LOWER(r.driver.fullName) LIKE LOWER(:search)
          OR EXISTS (
              SELECT s FROM RouteEntity r2 JOIN r2.stops s 
              WHERE r2 = r AND LOWER(s) LIKE LOWER(:search)
          )
      )
    """)
    Optional<List<RouteEntity>> findByDriverSchoolAndSearch(@Param("school") String school, @Param("search") String search, @Param("user") UserEntity user);

}
