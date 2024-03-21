package io.management.ua.configuration.properties;

import io.management.ua.utility.models.DefaultSpringSecurityUser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ApplicationSecurityPropertyBeans {
    @Bean(name = "basicUserDetailsService")
    public InMemoryUserDetailsManager basicUserDetailsService(@Qualifier("defaultUser") DefaultSpringSecurityUser defaultUser) {
        User.UserBuilder defaultUserDetailsBuilder = User
                .withUsername(defaultUser.getName())
                .password(defaultUser.getPassword())
                .roles(defaultUser.getRoles());

        UserDetails defaultUserDetails = defaultUserDetailsBuilder.build();

        return new InMemoryUserDetailsManager(defaultUserDetails);
    }


    @Bean(name = "defaultUser")
    @ConfigurationProperties(prefix = "spring.security.user")
    public DefaultSpringSecurityUser defaultUser() {
        return new DefaultSpringSecurityUser();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(7);
    }
}
