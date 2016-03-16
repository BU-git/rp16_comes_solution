package com.bionic.dao;

import com.bionic.model.User;

/**
 * 
 * @author vitalii.levash
 * @version 0.2
 */
public interface UserDao {

    public User findByUsername(String username);
    public void processUser(User user);

    User findById(int id);
}
