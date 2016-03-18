package com.bionic.service;

import com.bionic.dao.JobDao;
import com.bionic.dao.UserDao;
import com.bionic.exception.auth.impl.UserExistsException;
import com.bionic.model.Job;
import com.bionic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author vitalii.levash
 * @author Dima Budko
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;
    @Autowired
    private JobDao jobDao;


    public User addUser(User user) throws  UserExistsException {
        if (findByUsername(user.getEmail())!=null){
            throw new UserExistsException(user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @Override
    public Job findJobById(int id) { return jobDao.findById(id); }
}
