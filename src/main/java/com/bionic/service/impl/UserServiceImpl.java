package com.bionic.service.impl;

import com.bionic.dao.UserDao;
import com.bionic.dao.UserKeyDao;
import com.bionic.exception.auth.impl.PasswordIncorrectException;
import com.bionic.exception.auth.impl.UserExistsException;
import com.bionic.exception.auth.impl.UserNotExistsException;
import com.bionic.model.User;
import com.bionic.model.UserKey;
import com.bionic.model.WorkSchedule;
import com.bionic.model.dict.UserRoleEnum;
import com.bionic.service.MailService;
import com.bionic.service.UserService;
import com.bionic.service.WorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author vitalii.levash
 * @author Dima Budko
 */

@Service
@PropertySource("classpath:mail.properties")
public class UserServiceImpl implements UserService {

    @Resource
    private Environment env;
    public static final String URL = "config.url";

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;

    @Autowired
    private WorkScheduleService workScheduleService;

    @Autowired
    private UserKeyDao userKeyDao;

    @Transactional
    public User addUser(User user) throws UserExistsException {
        String email = user.getEmail();
        if (findByUsername(email) != null) {
            throw new UserExistsException(email);
        }
        user.setRole(UserRoleEnum.USER);
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getWorkSchedule() != null) {
            WorkSchedule workSchedule = workScheduleService.saveWorkSchedule(user.getWorkSchedule());
            user.setWorkSchedule(workSchedule);
        }

        long key = System.currentTimeMillis();
        UserKey userKey = new UserKey(key, email, "verification");
        userKey.setId(0);
        userKeyDao.saveAndFlush(userKey);
        mailService.sendVerification(email, key);

        return userDao.saveAndFlush(user);
    }

    @Transactional
    public User verifyUser(User user) {
        long key = System.currentTimeMillis();
        UserKey userKey = new UserKey(key, user.getEmail(), "verification");
        userKey.setId(0);
        userKeyDao.saveAndFlush(userKey);
        mailService.sendVerification(user.getEmail(), key);
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

    public User findByUserEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Transactional
    @Override
    public User saveUser(User user) {
        return userDao.saveAndFlush(user);
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
    @Transactional
    public void resetLink(String email) throws UserNotExistsException {
        User user = userDao.findByEmail(email);

        if (!(user == null)) {
            long key = System.currentTimeMillis();
            UserKey userKey = new UserKey(key, user.getEmail(), "reset_password");
            userKeyDao.saveAndFlush(userKey);
            mailService.sendResetPasswordLink(email, key);
        } else {
            throw new UserNotExistsException(email);
        }
    }

    @Override
    @Transactional
    public void resetPassword(long key) throws UserNotExistsException {
        UserKey userKey = userKeyDao.findBySecretForResetPass(key);
        if (userKey != null) {
            User user = userDao.findByEmail(userKey.getEmail());
            int tempPasswordLength = 10;
            String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";
            StringBuilder tempPassword = new StringBuilder();
            Random rand = new Random(System.currentTimeMillis());

            for (int i = 0; i < tempPasswordLength; i++) {
                int index = (int) (rand.nextDouble() * letters.length());
                tempPassword.append(letters.substring(index, index + 1));
            }
            mailService.sendTemporaryPassword(user.getEmail(), tempPassword.toString());
            user.setPassword(passwordEncoder.encode(tempPassword));
            user.setPasswordExpire(new Date(System.currentTimeMillis() + ONE_HOUR));
            userDao.saveAndFlush(user);
            userKeyDao.delete(userKey);
        } else {
            throw new UserNotExistsException();
        }
    }

    @Override
    @Transactional
    public void changePassword(int id, String oldPassword, String newPassword) throws PasswordIncorrectException {
        User user = userDao.findOne(id);

        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setPasswordExpire(new Date(System.currentTimeMillis() + TEN_YEARS));
            userDao.saveAndFlush(user);
        } else {
            throw new PasswordIncorrectException();
        }
    }

    @Override
    @Transactional
    public void enableAccount(long key) throws UserNotExistsException {
        UserKey userKey = userKeyDao.findBySecretForVerification(key);
        if (userKey != null) {
            User user = userDao.findByEmail(userKey.getEmail());
            user.setVerified(true);
            userDao.saveAndFlush(user);
            userKeyDao.delete(userKey);
        } else {
            throw new UserNotExistsException();
        }
    }

    /**
     *
     * @return Authentication User
     */
    @Override
    public User getAuthUser()  {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        User user=userDao.findByName(name);
        return user;
    }
}
