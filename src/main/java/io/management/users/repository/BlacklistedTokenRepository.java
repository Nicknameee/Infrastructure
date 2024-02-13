package io.management.users.repository;


import io.management.users.models.BlacklistedToken;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface BlacklistedTokenRepository extends KeyValueRepository<BlacklistedToken, String> {
}
