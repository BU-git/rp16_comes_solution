package com.bionic.exception.auth.impl;

import com.bionic.exception.auth.AuthException;

/**
 * author Dima Budko
 * v.0.1
 */
public class LinkUsedException extends AuthException{
    public LinkUsedException(){
        super("This link has already been used");
    }
}
