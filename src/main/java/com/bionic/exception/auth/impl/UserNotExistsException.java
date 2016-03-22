package com.bionic.exception.auth.impl;

import com.bionic.exception.auth.AuthException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Pavel Boiko
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotExistsException extends AuthException {

    public UserNotExistsException(){
        super("User with this email does not exist");
    }

    public UserNotExistsException(final String email){

        super(String.format("Email '%s' does not exist in DB.", email));
    }

}
