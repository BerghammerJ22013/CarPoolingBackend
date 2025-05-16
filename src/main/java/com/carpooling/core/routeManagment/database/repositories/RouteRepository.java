package com.carpooling.core.routeManagment.database.repositories;

import com.carpooling.core.routeManagment.database.entities.RouteEntity;
import org.springframework.data.repository.CrudRepository;

public interface RouteRepository extends CrudRepository<RouteEntity, Long> {
}
