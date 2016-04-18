package com.bionic.exception.auth.impl;

import com.bionic.exception.auth.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author vitalii.levash
 */

public class UserExistsException extends AuthException {
    public UserExistsException(){
        super("Email for user exists");
    }
    public UserExistsException(final String email){
        super(String.format("Email '%s' already exists in DB.", email));
    }

}
