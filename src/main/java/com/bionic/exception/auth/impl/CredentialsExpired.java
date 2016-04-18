package com.bionic.exception.auth.impl;

import com.bionic.exception.auth.AuthException;

public class CredentialsExpired extends AuthException {
    public CredentialsExpired(){
        super("Credentials Expired");
    }
}