package com.carpooling.core.routeManagment.database.entities;

import com.carpooling.core.userManagement.database.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "route_passengers")
public class RoutePassengerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private RouteEntity route;

    @ManyToOne
    private UserEntity user;

    private String pickupLocation;
    private String note;
}

