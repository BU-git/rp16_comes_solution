package com.bionic.exception.web.impl;

import com.bionic.exception.web.WebException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Pavel Boiko
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends WebException {
    
    public UserNotFoundException() { super("User not found"); }
}
