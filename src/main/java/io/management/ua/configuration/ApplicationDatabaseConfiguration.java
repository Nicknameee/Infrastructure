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
@EnableJpaRepositories(entityManagerFactoryRef = "databaseEntityManagerFactory",
        transactionManagerRef = "databaseTransactionManager",
        basePackages = "io.management.ua")
public class ApplicationDatabaseConfiguration {
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSourceProperties databaseDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource databaseDataSource(@Qualifier("databaseDataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "databaseEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean databaseEntityManagerFactory(@Qualifier("databaseDataSource") DataSource databaseDataSource,
                                                                             EntityManagerFactoryBuilder builder) {

        return builder.dataSource(databaseDataSource)
                .packages("io.management.ua")
                .persistenceUnit("database")
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager databaseTransactionManager(@Qualifier("databaseEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

}
