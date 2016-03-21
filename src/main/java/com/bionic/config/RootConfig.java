package com.bionic.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Pavel Boiko
 */
@Configuration
@Import({MailConfig.class, PersistenceConfig. class,
        SecurityConfig.class, SecurityInitializer.class})
@ComponentScan(basePackages = {"com.bionic.dao", "com.bionic.model",
        "com.bionic.service", "com.bionic.controllers",
        "com.bionic.config","com.bionic.logging"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
        })
public class RootConfig extends WebMvcConfigurerAdapter {

}
