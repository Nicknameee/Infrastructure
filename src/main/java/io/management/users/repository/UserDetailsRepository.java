package io.management.users.repository;

import io.management.users.models.UserDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetailsModel, Long> {
    Optional<UserDetailsModel> findAbstractUserModelByUsername(String username);
}
