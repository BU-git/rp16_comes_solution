package com.bionic.service;

import com.bionic.model.User;

import java.util.List;

public interface UserService {


    User findById(int id);
    User addUser(User user);
    void delete(int id);
    User findByUsername(String name);
    User editUser(User user);
    List<User> getAll();

}
