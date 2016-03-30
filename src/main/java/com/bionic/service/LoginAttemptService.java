/**
 * Custom user lock
 */
package com.bionic.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author vitalii.levash
 */
@Service
public class LoginAttemptService {

    private final int MAX_FAILED_ATTEMPTS=15;
    private final int TIME_FOR_LOCK=30;

    private LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService(){
        attemptsCache = CacheBuilder.newBuilder()
                                    .expireAfterWrite(TIME_FOR_LOCK, TimeUnit.MINUTES)
                                    .build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(final String key) {
                return 0;
            }
        });
    }

    public void loginSucceeded(final String key) {
        attemptsCache.invalidate(key);
    }

    public void loginFailed(final String key) {
        int attempts = 0;
        try {
            attempts = attemptsCache.get(key);
        } catch (final ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(final String key) {
        try {
            return attemptsCache.get(key) >= MAX_FAILED_ATTEMPTS;
        } catch (final ExecutionException e) {
            return false;
        }
    }
}
