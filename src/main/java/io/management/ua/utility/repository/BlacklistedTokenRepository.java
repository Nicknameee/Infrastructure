package io.management.ua.utility.repository;

import io.management.ua.utility.models.BlacklistedToken;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

import java.util.UUID;

public interface BlacklistedTokenRepository extends KeyValueRepository<BlacklistedToken, UUID> {
    boolean existsByToken(String token);
}
