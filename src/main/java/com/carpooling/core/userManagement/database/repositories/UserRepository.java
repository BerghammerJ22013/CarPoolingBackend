package com.carpooling.core.userManagement.database.repositories;

import com.carpooling.core.userManagement.database.entities.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
    boolean existsByFullname(String fullName);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByFullname(String fullname);
}
