package com.bionic.config.security;

import com.bionic.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


/**
 * @author vitalii.levash
 */
@Component
public class AuthenticationSuccessEventListener  implements ApplicationListener<AuthenticationSuccessEvent> {
    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public void onApplicationEvent(final AuthenticationSuccessEvent e) {
        final Authentication auth = (Authentication ) e.getAuthentication().getDetails();
        if (auth != null) {
            loginAttemptService.loginSucceeded(auth.getName());
        }
    }

}
