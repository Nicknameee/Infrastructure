package io.management.ua.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "resourcesEntityManagerFactory",
        transactionManagerRef = "resourcesTransactionManager",
        basePackages = "io.management.resources")
public class ApplicationDatabaseResourcesConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-resources")
    public DataSourceProperties resourcesDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConditionalOnBean(name = "resourcesDataSourceProperties")
    public DataSource resourcesDataSource(@Qualifier("resourcesDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "resourcesEntityManagerFactory")
    @ConditionalOnBean(name = "resourcesDataSourceProperties")
    public LocalContainerEntityManagerFactoryBean resourcesEntityManagerFactory(@Qualifier("resourcesDataSource") DataSource resourcesDataSource,
                                                                             EntityManagerFactoryBuilder builder) {

        return builder.dataSource(resourcesDataSource)
                .packages("io.management.resources")
                .persistenceUnit("resources")
                .build();
    }

    @Bean
    @ConditionalOnBean(name = "resourcesDataSourceProperties")
    public PlatformTransactionManager resourcesTransactionManager(@Qualifier("resourcesEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway(@Qualifier("resourcesDataSource") DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/migrations/resources")
                .outOfOrder(true)
                .baselineOnMigrate(true)
                .load();
    }
}
