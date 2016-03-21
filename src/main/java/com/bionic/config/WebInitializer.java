package com.bionic.config;

import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @author Sasha Chepurnoy
 *
 * This class is a replacement for WEB xml descriptor in java based configuration
 *
 */
@Order(1)
public class WebInitializer  extends
        AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { RootConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {

        return new Class[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {

        return new String[] { "/" };
    }
}