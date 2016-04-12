package com.bionic.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Pavel Boiko
 */
@Configuration
@EnableJpaRepositories("com.bionic.dao")
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
public class PersistenceConfig {

    private static final String PROP_DATABASE_DRIVER = "db.driver";
    private static final String PROP_DATABASE_PASSWORD = "db.password";
    private static final String PROP_DATABASE_URL = "db.url";
    private static final String PROP_DATABASE_USERNAME = "db.username";
    private static final String PROP_HIBERNATE_DIALECT = "db.hibernate.dialect";
    private static final String PROP_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROP_ENTITYMANAGER_PACKAGES_TO_SCAN = "db.entitymanager.packages.to.scan";
    private static final String PROP_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROP_LAZY_LOAD="hibernate.enable_lazy_load_no_trans";

    private static final String PROP_FORMAT_SQL="hibernate.format_sql";
    private static final String PROP_SHOW_SQL_COMENT="hibernate.use_sql_comments";

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(env.getRequiredProperty(PROP_DATABASE_DRIVER));
        ds.setUrl(env.getRequiredProperty(PROP_DATABASE_URL));
        ds.setUsername(env.getRequiredProperty(PROP_DATABASE_USERNAME));
        ds.setPassword(env.getRequiredProperty(PROP_DATABASE_PASSWORD));
        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.setPackagesToScan(env.getRequiredProperty(PROP_ENTITYMANAGER_PACKAGES_TO_SCAN).split(","));
        em.setDataSource(dataSource());
        em.setPersistenceUnitName("TruckDriverDB");
        em.setJpaProperties(getHibernateProperties());
        return em;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put(PROP_HIBERNATE_DIALECT, env.getRequiredProperty(PROP_HIBERNATE_DIALECT));
        properties.put(PROP_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROP_HIBERNATE_SHOW_SQL));
        properties.put(PROP_HIBERNATE_HBM2DDL_AUTO, env.getRequiredProperty(PROP_HIBERNATE_HBM2DDL_AUTO));
        properties.put(PROP_LAZY_LOAD,env.getRequiredProperty(PROP_LAZY_LOAD));
        properties.put(PROP_FORMAT_SQL,env.getRequiredProperty(PROP_FORMAT_SQL));
        properties.put(PROP_SHOW_SQL_COMENT,env.getRequiredProperty(PROP_SHOW_SQL_COMENT));

        return properties;
    }
}
