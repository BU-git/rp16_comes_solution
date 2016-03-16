package com.bionic.service;

import com.bionic.model.User;

public interface UserService {

    public User findByUsername(String username);
    public void processUser(User user);
    
}
