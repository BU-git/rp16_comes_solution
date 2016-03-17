package com.bionic.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import({PersistenceConfig.class, MailConfig.class})
@EnableJpaRepositories("com.bionic.dao")
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackages = {"com.bionic.dao", "com.bionic.model", "com.bionic.service","com.bionic.config"})

public class MainConfig {
}
