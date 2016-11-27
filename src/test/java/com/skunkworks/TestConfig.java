package com.skunkworks;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * stole on 26.11.16.
 */
@Configuration
@EnableConfigurationProperties
class TestConfig {

    @Bean
    @ConfigurationProperties(prefix = "datasource")
    @DependsOn("dataSourceInitializer")
    DataSource dataSource() {
        return new DriverManagerDataSource();
    }

    @Bean
    @ConfigurationProperties(prefix = "buildDatasource")
    DataSource builderDataSource() {
        return new DriverManagerDataSource();
    }

    @Bean
    DataSourceInitializer dataSourceInitializer() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setSeparator("GO");

        populator.addScript(new ClassPathResource("database/initDb.sql"));
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(builderDataSource());
        initializer.setDatabasePopulator(populator);
        return initializer;
    }
}
