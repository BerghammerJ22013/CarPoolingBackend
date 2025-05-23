package com.carpooling.core.routeManagment.database.entities;

import com.carpooling.core.userManagement.database.entities.UserEntity;
import com.carpooling.core.userManagement.rest.resources.UserResource;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "routes")
public class RouteEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fromLocation;
    private LocalDate date;
    private LocalTime time;
    private int seatsAvailable;

    @ElementCollection
    private List<String> stops;

    @ManyToOne
    private UserEntity driver;

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoutePassengerEntity> passengers = new ArrayList<>();

}
