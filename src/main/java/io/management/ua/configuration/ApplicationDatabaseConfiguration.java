package io.management.ua.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "databaseEntityManagerFactory",
        transactionManagerRef = "databaseTransactionManager",
        basePackages = "io.management.ua")
public class ApplicationDatabaseConfiguration {
    @Bean
    @ConditionalOnProperty(prefix = "spring.datasource", name = "url")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties databaseDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConditionalOnBean(name = "databaseDataSourceProperties")
    public DataSource databaseDataSource(@Qualifier("databaseDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "databaseEntityManagerFactory")
    @ConditionalOnBean(name = "databaseDataSourceProperties")
    public LocalContainerEntityManagerFactoryBean databaseEntityManagerFactory(@Qualifier("databaseDataSource") DataSource databaseDataSource,
                                                                             EntityManagerFactoryBuilder builder) {

        return builder.dataSource(databaseDataSource)
                .packages("io.management.ua")
                .persistenceUnit("database")
                .build();
    }

    @Bean
    @ConditionalOnBean(name = "databaseDataSourceProperties")
    public PlatformTransactionManager databaseTransactionManager(@Qualifier("databaseEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.flyway")
    public FlywayProperties databaseFlywayProperties() {
        return new FlywayProperties();
    }

    @Bean(initMethod = "migrate")
    @ConditionalOnBean(name = "databaseFlywayProperties")
    @ConditionalOnProperty(prefix = "spring.flyway", name = "enabled", havingValue = "true")
    public Flyway databaseFlyway(@Qualifier("databaseFlywayProperties") FlywayProperties databaseFlywayProperties) {
        return Flyway.configure()
                .outOfOrder(databaseFlywayProperties.isOutOfOrder())
                .baselineOnMigrate(databaseFlywayProperties.isBaselineOnMigrate())
                .locations(databaseFlywayProperties.getLocations().toArray(new String[0]))
                .dataSource(
                        databaseFlywayProperties.getUrl(),
                        databaseFlywayProperties.getUser(),
                        databaseFlywayProperties.getPassword())
                .load();
    }

    @Bean
    @Primary
    @ConditionalOnBean(name = "databaseDataSource")
    public JdbcTemplate databaseJdbcTemplate(@Qualifier("databaseDataSource") DataSource databaseDataSource) {
        return new JdbcTemplate(databaseDataSource);
    }
}
