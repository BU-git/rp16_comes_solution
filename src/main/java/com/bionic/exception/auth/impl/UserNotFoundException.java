package com.bionic.exception.auth.impl;

import com.bionic.exception.auth.AuthException;

/**
 * @author Pavel Boiko
 */
public class UserNotFoundException extends AuthException {
    
    public UserNotFoundException() { super("User not found"); }
}
