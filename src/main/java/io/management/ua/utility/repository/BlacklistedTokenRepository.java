package io.management.ua.utility.repository;

import io.management.ua.utility.models.BlacklistedToken;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface BlacklistedTokenRepository extends KeyValueRepository<BlacklistedToken, String> {
}
