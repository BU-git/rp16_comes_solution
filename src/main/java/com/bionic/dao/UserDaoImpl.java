package com.bionic.dao;

import com.bionic.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public User findByUsername(String username) {
        return entityManager.find(User.class, username);
    }
}
