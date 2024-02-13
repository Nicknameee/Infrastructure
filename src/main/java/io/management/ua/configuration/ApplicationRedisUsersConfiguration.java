package io.management.ua.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(basePackages = "io.management.users")
public class ApplicationRedisUsersConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.redis-users")
    public RedisProperties usersRedisProperties() {
        return new RedisProperties();
    }

    @Bean
    public RedisConnectionFactory usersRedisConnectionFactory(@Qualifier("usersRedisProperties") RedisProperties redisProperties) {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());

        lettuceConnectionFactory.setDatabase(redisProperties.getDatabase());
        lettuceConnectionFactory.setPassword(redisProperties.getPassword());

        return lettuceConnectionFactory;
    }
}
