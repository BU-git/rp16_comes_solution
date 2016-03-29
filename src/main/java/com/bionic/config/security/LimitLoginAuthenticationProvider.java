package com.bionic.config.security;

import com.bionic.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * Created by vitalii.levash on 29.03.2016.
 */
@Component
public class LimitLoginAuthenticationProvider  extends DaoAuthenticationProvider {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        try {

            Authentication auth = super.authenticate(authentication);
            loginAttemptService.loginSucceeded(authentication.getName());

            return auth;

        } catch (BadCredentialsException e) {

            loginAttemptService.loginFailed(authentication.getName());
            throw e;

        } catch (LockedException e) {
            throw e;
           /*

            */
        }

    }

}
