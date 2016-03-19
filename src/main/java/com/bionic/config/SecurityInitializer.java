package com.bionic.config;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @author vitalii.levash
 */
@Order(2)
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
    /*public SecurityInitializer() {
        super(SecurityConfig.class);
    }*/
}
