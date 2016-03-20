package com.bionic.service;

import com.bionic.dao.JobDao;
import com.bionic.dao.UserDao;
import com.bionic.exception.auth.impl.PasswordIncorrectException;
import com.bionic.exception.auth.impl.UserExistsException;
import com.bionic.exception.auth.impl.UserNotExistsException;
import com.bionic.model.Job;
import com.bionic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author vitalii.levash
 * @author Dima Budko
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private JobDao jobDao;

    @Autowired
    private MailService mailService;

    private static final long TEN_YEARS = 315_000_000_000L;
    private static final int ONE_HOUR = 3_600_000;

    @Transactional
    public User addUser(User user) throws  UserExistsException {
        if (findByUsername(user.getEmail())!=null){
            throw new UserExistsException(user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userDao.saveAndFlush(user);
    }

    @Transactional
    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public User findByUsername(String name) {
        return userDao.findByName(name);
    }

    public User findByUserEmail(String email) { return userDao.findByEmail(email); }

    @Transactional
    @Override
    public User saveUser(User User) {
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

    @Override
    @Transactional
    public void resetPassword(String email) throws UserNotExistsException {
        User user = userDao.findByEmail(email);
        if (!(user == null)) {
            int tempPasswordLength = 10;
            String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";
            String tempPassword = "";
            Random rand = new Random(43);

            for (int i=0; i<tempPasswordLength; i++)
            {
                int index = (int)(rand.nextDouble()*letters.length());
                tempPassword += letters.substring(index, index+1);
            }

            String subject = "Password reset";
            String message = "Your new temporary password: " + tempPassword + " \n";
            message += "Password is valid for 1 hour.";
            mailService.sendMail(email, subject, message);

            user.setPassword(passwordEncoder.encode(tempPassword));
            user.setPasswordExpire(new Date(System.currentTimeMillis() + ONE_HOUR));
            userDao.saveAndFlush(user);

        } else {
            throw new UserNotExistsException(email);
        }

    }

    @Override
    @Transactional
    public void changePassword(String email, String oldPassword, String newPassword) throws PasswordIncorrectException {
        User user = userDao.findByEmail(email);

        if(passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setPasswordExpire(new Date(System.currentTimeMillis() + TEN_YEARS));
            userDao.saveAndFlush(user);
        } else {
            throw new PasswordIncorrectException();
        }

    }
}
