package com.bionic.config;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.jpa.PersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaDialect;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

@Configuration
@EnableJpaRepositories("com.bionic.dao")
@ComponentScan(basePackages = {"com.bionic.dao", "com.bionic.model",
        "com.bionic.service", "com.bionic.controllers",
        "com.bionic.config","com.bionic.logging"})
@EnableTransactionManagement
public class PersistenceConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://server14.webhostnow.eu/tda_db");
        ds.setUsername("tda_test");
        ds.setPassword("12345");
        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPersistenceProviderClass(PersistenceProvider.class);
        em.setPackagesToScan("com.bionic.dao", "com.bionic.model", "com.bionic.service");
        em.setDataSource(dataSource());
        em.setPersistenceUnitName("TruckDriverDB");
        em.setJpaVendorAdapter(jpaVendorAdaper());
        em.setJpaDialect(new EclipseLinkJpaDialect());
        em.setJpaPropertyMap(additionalProperties());
        return em;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdaper() {
        EclipseLinkJpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setDatabasePlatform("org.eclipse.persistence.platform.database.MySQLPlatform");
        return vendorAdapter;
    }

    private Map<String, Object> additionalProperties() {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("eclipselink.weaving", "false");
        properties.put("eclipselink.logging.level", "WARNING");
        properties.put("eclipselink.logging.level.sql", "WARNING");

        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        transactionManager.setDataSource(dataSource());
        transactionManager.setJpaDialect(new EclipseLinkJpaDialect());
        return transactionManager;
    }
}
