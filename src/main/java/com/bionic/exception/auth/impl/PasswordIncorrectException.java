package com.bionic.exception.auth.impl;

import com.bionic.exception.auth.AuthException;

/**
 * @author Pavel Boiko
 */
public class PasswordIncorrectException extends AuthException {
    public PasswordIncorrectException() {
        super("The password is incorrect");
    }
}
