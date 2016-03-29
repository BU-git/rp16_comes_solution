package com.bionic.exception.auth.impl;

import com.bionic.exception.auth.AuthException;

/**
 * author Dima Budko
 * v.0.1
 *
 */
public class UserNotVerifiedException extends AuthException{
    public UserNotVerifiedException() { super("User is not verified"); }
}
