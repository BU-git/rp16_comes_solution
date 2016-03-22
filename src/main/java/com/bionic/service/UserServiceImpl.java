package com.bionic.service;

import com.bionic.dao.JobDao;
import com.bionic.dao.ResetKeyDao;
import com.bionic.dao.UserDao;
import com.bionic.exception.auth.impl.LinkUsedException;
import com.bionic.exception.auth.impl.PasswordIncorrectException;
import com.bionic.exception.auth.impl.UserExistsException;
import com.bionic.exception.auth.impl.UserNotExistsException;
import com.bionic.model.Job;
import com.bionic.model.ResetKey;
import com.bionic.model.User;
import com.bionic.model.dict.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
    private JobDao jobDao;

    @Autowired
    private MailService mailService;

    @Autowired
    private ResetKeyDao resetKeyDao;

    private static final long TEN_YEARS = 315_000_000_000L;
    private static final int ONE_HOUR = 3_600_000;

    @Transactional
    public User addUser(User user) throws UserExistsException {
        if (findByUsername(user.getEmail()) != null) {
            throw new UserExistsException(user.getEmail());
        }
        user.setRole(UserRoleEnum.USER);
        user.setEnabled(true);
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
    public Job findJobById(int id) {
        return jobDao.findById(id);
    }

    @Override
    @Transactional
    public void resetLink(String email) throws UserNotExistsException {
        User user = userDao.findByEmail(email);

        if (!(user == null)) {
            long key = System.currentTimeMillis();
            ResetKey resetKey = new ResetKey(key, user.getEmail());
            resetKeyDao.saveAndFlush(resetKey);
            String sentURL = "";
            sentURL = env.getProperty(URL) + "/password?key=" + key;

            String subject = "Password reset";
            String message = "Your link to password reset: " + sentURL + " \n";
            mailService.sendMail(email, subject, message);
        } else {
            throw new UserNotExistsException(email);
        }
    }

    @Override
    @Transactional
    public void resetPassword(long key) throws LinkUsedException {
        ResetKey resetKey = resetKeyDao.findBySecret(key);
        if (resetKey != null) {
            User user = userDao.findByEmail(resetKey.getEmail());
            int tempPasswordLength = 10;
            String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";
            String tempPassword = "";
            Random rand = new Random(System.currentTimeMillis());

            for (int i = 0; i < tempPasswordLength; i++) {
                int index = (int) (rand.nextDouble() * letters.length());
                tempPassword += letters.substring(index, index + 1);
            }
            String subject = "Password reset";
            String message = "Your new temporary password: " + tempPassword + " \n";
            message += "Password is valid for 1 hour.";
            mailService.sendMail(user.getEmail(), subject, message);
            user.setPassword(passwordEncoder.encode(tempPassword));
            user.setPasswordExpire(new Date(System.currentTimeMillis() + ONE_HOUR));
            userDao.saveAndFlush(user);
            resetKeyDao.delete(resetKey);
        } else {
            throw new LinkUsedException();
        }
    }

    @Override
    @Transactional
    public void changePassword(String email, String oldPassword, String newPassword) throws PasswordIncorrectException {
        User user = userDao.findByEmail(email);

        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setPasswordExpire(new Date(System.currentTimeMillis() + TEN_YEARS));
            userDao.saveAndFlush(user);
        } else {
            throw new PasswordIncorrectException();
        }
    }
}
