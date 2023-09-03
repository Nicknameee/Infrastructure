package io.management.ua.utility;

import io.management.ua.utility.models.AbstractUserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AbstractUserModel, Integer> {
    Optional<AbstractUserModel> findAbstractUserModelByUsername(String username);
}
