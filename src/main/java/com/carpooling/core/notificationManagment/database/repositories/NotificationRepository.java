package com.carpooling.core.notificationManagment.database.repositories;

import com.carpooling.core.notificationManagment.database.entities.NotificationEntity;
import com.carpooling.core.userManagement.database.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends CrudRepository<NotificationEntity, Long> {
    Optional<List<NotificationEntity>> findByReceiver(UserEntity receiver);

    @Query("""
            SELECT n FROM NotificationEntity n 
            WHERE n.receiver = :receiver
            AND DATE(n.timeStamp) = CURRENT_DATE
            """)
    Optional<List<NotificationEntity>> findByReceiverOfToday(@Param("receiver") UserEntity receiver);
}
