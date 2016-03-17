package com.bionic.exception.auth;

import com.bionic.exception.AppException;

/**
 * @author vitalii.levash
 */
public class AuthException extends AppException {
    public AuthException(final String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthException(Throwable cause) {
        super(cause);
    }
}
