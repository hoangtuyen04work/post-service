package com.service.post_service.repo;

import com.service.post_service.entity.UserIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserIdRepo extends JpaRepository<UserIdEntity, String> {
    @Query("SELECT u FROM UserIdEntity  u WHERE u.userId = :userId")
    Optional<UserIdEntity> findByUserId(@Param("userId") String userId);
    boolean existsByUserId(String userId);
}
