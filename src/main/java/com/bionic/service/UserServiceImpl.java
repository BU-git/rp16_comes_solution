package com.bionic.service;

import com.bionic.dao.UserDao;
import com.bionic.model.User;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private UserDao userDao;

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Transactional
	public void processUser(User user) {
		ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        String hashedPass = encoder.encodePassword(user.getPassword(), user.getUsername());
		user.setPassword(hashedPass);
	}
    

}
