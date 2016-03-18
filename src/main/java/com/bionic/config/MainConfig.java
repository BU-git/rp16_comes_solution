package com.bionic.config;

import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;



@Configuration
@EnableJpaRepositories("com.bionic.dao")
@EnableTransactionManagement(proxyTargetClass = true)
@Import({PersistenceConfig.class, MailConfig.class})
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"com.bionic.dao", "com.bionic.model", "com.bionic.service", "com.bionic.controllers","com.bionic.config","com.bionic.logging"})
public class MainConfig extends WebMvcConfigurerAdapter {


    //Resources declaration
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**")
//                .addResourceLocations("/resources/");
//    }



    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver
                = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/view/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
