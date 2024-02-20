package io.management.users.repository;

import io.management.users.models.UserDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetailsModel, Long> {
    @Query("SELECT user FROM UserDetailsModel user WHERE (user.username IS NOT NULL AND user.username = :login) OR (user.email IS NOT NULL AND user.email = :login) OR (user.telegramUsername IS NOT NULL AND user.telegramUsername = :login)")
    Optional<UserDetailsModel> findUserDetailsByLogin(@Param("login") String login);
}
