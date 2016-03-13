package com.bionic.dao;

import com.bionic.model.User;

public interface UserDao {

    public User findByUsername(String username);
}
