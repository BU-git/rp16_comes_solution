package com.bionic.config.security;

import com.bionic.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by vitalii.levash on 29.03.2016.
 */
@Service
public class LimitLoginAuthenticationProvider  extends DaoAuthenticationProvider {

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    @Override
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        super.setUserDetailsService(userDetailsService);
    }


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
            String error="Account is locked " + authentication.getName();
            /*
            if (loginAttemptService.isBlocked(authentication.getName())){
                error="User is locked"+authentication.getName();
            }
            */
            throw new LockedException(error);

        }

    }

}
