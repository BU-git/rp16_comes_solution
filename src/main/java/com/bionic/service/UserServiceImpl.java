package com.bionic.service;

import com.bionic.dao.UserDao;
import com.bionic.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

//    public User findByUsername(String username) {
//        return userDao.findByUsername(username);
//    }
//
//    @Override
//    public User findById(int id) {
//        return userDao.findById(id);
//    }
//
//    @Transactional
//	public void processUser(User user) {
//		ShaPasswordEncoder encoder = new ShaPasswordEncoder();
//        String hashedPass = encoder.encodePassword(user.getPassword(), user.getEmail());
//		user.setPassword(hashedPass);
//	}

    @Autowired
    private UserDao userDao;

    @Override
    public User addUser(User user) {
        User savedUser = userDao.saveAndFlush(user);
        return savedUser;
    }

    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public User findByUsername(String name) {
        return userDao.findByName(name);
    }

    @Override
    public User editUser(User User) {
        return userDao.saveAndFlush(User);
    }

    @Override
    public List<User> getAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(int id) {
        return userDao.findOne(id);
    }
}
