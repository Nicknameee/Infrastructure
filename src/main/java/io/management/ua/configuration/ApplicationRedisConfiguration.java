package io.management.ua.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(basePackages = "io.management.ua")
public class ApplicationRedisConfiguration {
}
