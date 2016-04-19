package com.bionic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author Pavel Boiko
 */
@Configuration
@EnableJpaRepositories("com.bionic.dao")
@EnableTransactionManagement
public class TestPersistenceConfig {

    private static final String PROP_DATABASE_DRIVER = "db.driver";

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(env.getRequiredProperty(PROP_DATABASE_DRIVER));
        ds.setUrl("jdbc:mysql://localhost:3306/tdalocal");
        ds.setUsername("root");
        ds.setPassword("root");
        return ds;
    }

}
