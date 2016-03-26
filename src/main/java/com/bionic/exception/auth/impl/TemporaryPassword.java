package com.bionic.exception.auth.impl;

import com.bionic.exception.auth.AuthException;

public class TemporaryPassword extends AuthException {
    public TemporaryPassword(){
        super("You have temporary password. It have to be changed!");
    }
}