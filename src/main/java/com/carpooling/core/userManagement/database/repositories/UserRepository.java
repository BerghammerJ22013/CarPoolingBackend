package com.carpooling.core.userManagement.database.repositories;

import com.carpooling.core.userManagement.database.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    boolean existsByEmail(String email);
    boolean existsByFullName(String fullName);
    UserEntity findByEmail(String email);
    List<UserEntity> findAllBySchool(String school);

}
