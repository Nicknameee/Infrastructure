package io.management.ua.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(basePackages = "io.management.ua")
public class ApplicationRedisConfiguration {
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public RedisProperties redisProperties() {
        return new RedisProperties();
    }

    @Primary
    @Bean
    public RedisConnectionFactory redisConnectionFactory(@Qualifier("redisProperties") RedisProperties redisProperties) {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());

        lettuceConnectionFactory.setDatabase(redisProperties.getDatabase());
        lettuceConnectionFactory.setPassword(redisProperties.getPassword());

        return lettuceConnectionFactory;
    }
}
