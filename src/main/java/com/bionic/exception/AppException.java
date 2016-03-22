package com.bionic.exception;

/**
 * Created by leo on 17.03.2016.
 */
public class AppException extends Exception {

    public AppException(final String message) {
        super(message);
    }

    public AppException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AppException(final Throwable cause) {
        super(cause);
    }
}
