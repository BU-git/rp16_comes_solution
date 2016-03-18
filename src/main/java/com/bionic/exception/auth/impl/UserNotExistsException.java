package com.bionic.exception.auth.impl;

import com.bionic.exception.auth.AuthException;

/**
 * @author Pavel Boiko
 */
public class UserNotExistsException extends AuthException {

    public UserNotExistsException(){
        super("User with this email does not exist");
    }

    public UserNotExistsException(final String email){

        super(String.format("Email '%s' does not exist in DB.", email));
    }

}
