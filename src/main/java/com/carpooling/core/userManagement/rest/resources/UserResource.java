package com.carpooling.core.userManagement.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserResource {
    private long id;
    private String fullname;
    private String email;
    private String school;
}
