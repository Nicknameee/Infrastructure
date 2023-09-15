package io.management.ua.utility.repository;

import io.management.ua.utility.models.UserDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetailsModel, Integer> {
    Optional<UserDetailsModel> findAbstractUserModelByUsername(String username);
}
