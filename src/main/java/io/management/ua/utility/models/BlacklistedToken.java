package io.management.ua.utility.models;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Data
@RedisHash("blacklistedToken")
public class BlacklistedToken {
    @Id
    private String token;
    private String username;
}
