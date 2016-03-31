package com.bionic.service;

/**
 * @author vitalii.levash
 */
public interface LoginAttemptService {

    public void loginSucceeded(final String key);

    public void loginFailed(final String key);

    public boolean isBlocked(final String key);
}
