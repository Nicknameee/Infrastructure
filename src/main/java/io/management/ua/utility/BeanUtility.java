package io.management.ua.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class BeanUtility {
    @Bean
    @Order
    public UserDetailsService getUserDetailsService() {
        return username -> null;
    }
}
