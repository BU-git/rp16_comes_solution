package com.bionic.service;

import com.bionic.dao.UserDao;
import com.bionic.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;

    @Transactional
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

}
