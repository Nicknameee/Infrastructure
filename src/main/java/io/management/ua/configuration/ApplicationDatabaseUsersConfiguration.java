package io.management.ua.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "usersEntityManagerFactory",
        transactionManagerRef = "usersTransactionManager",
        basePackages = "io.management.users")
public class ApplicationDatabaseUsersConfiguration {
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-users")
    public DataSourceProperties usersDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource usersDataSource(@Qualifier("usersDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "usersEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean usersEntityManagerFactory(@Qualifier("usersDataSource") DataSource cardsDataSource,
                                                                             EntityManagerFactoryBuilder builder) {

        return builder.dataSource(cardsDataSource)
                .packages("io.management.users")
                .persistenceUnit("users")
                .build();
    }

    @Bean
    public PlatformTransactionManager usersTransactionManager(@Qualifier("usersEntityManagerFactory")
                                                               EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

}
