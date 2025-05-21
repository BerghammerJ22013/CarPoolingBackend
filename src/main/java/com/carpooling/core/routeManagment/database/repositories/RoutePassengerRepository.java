package com.carpooling.core.routeManagment.database.repositories;

import com.carpooling.core.routeManagment.database.entities.RouteEntity;
import com.carpooling.core.routeManagment.database.entities.RoutePassengerEntity;
import com.carpooling.core.userManagement.database.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoutePassengerRepository extends CrudRepository<RoutePassengerEntity, Long> {
    @Query("""
            SELECT rp FROM RoutePassengerEntity rp 
            WHERE rp.user = :user AND rp.route = :route
            """)
    Optional<RoutePassengerEntity> findByUserAndRoute(@Param("user") UserEntity userEntity, @Param("route") RouteEntity routeEntity);
}
