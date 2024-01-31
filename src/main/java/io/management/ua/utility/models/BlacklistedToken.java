package io.management.ua.utility.models;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.util.UUID;

@Data
@RedisHash("blacklistedToken")
public class BlacklistedToken {
    @Id
    private UUID id = UUID.randomUUID();
    private String token;
    private String username;
}
