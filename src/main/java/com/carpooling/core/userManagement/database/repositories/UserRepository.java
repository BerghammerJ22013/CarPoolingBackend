package com.carpooling.core.userManagement.database.repositories;

import com.carpooling.core.userManagement.database.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
