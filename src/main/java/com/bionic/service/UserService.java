package com.bionic.service;

import com.bionic.exception.auth.impl.PasswordIncorrectException;
import com.bionic.exception.auth.impl.UserExistsException;
import com.bionic.exception.auth.impl.UserNotExistsException;
import com.bionic.model.User;

import java.util.List;

public interface UserService {

    long TEN_YEARS = 315_000_000_000L;
    int ONE_HOUR = 3_600_000;

    User findById(int id);
    User addUser(User user) throws UserExistsException;
    public User verifyUser(User user);
    void delete(int id);
    User findByUsername(String name);
    User findByUserEmail(String email);
    User saveUser(User user);
    List<User> getAll();
//    Job findJobById(int id);
    void resetLink(String email) throws UserNotExistsException;
    void resetPassword(long key) throws UserNotExistsException;
    void changePassword(int id, String oldPassword, String newPassword) throws PasswordIncorrectException;
    void enableAccount(long key) throws UserNotExistsException;

    User getAuthUser() ;
}
