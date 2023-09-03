package io.management.ua.utility;

import io.management.ua.utility.models.UserDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetailsModel, Integer> {
    Optional<UserDetailsModel> findAbstractUserModelByUsername(String username);
}
