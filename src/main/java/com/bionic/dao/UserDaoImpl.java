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

	@Override
	public void processUser(User user) {
		if (user.getId()==0){
			entityManager.persist(user);
		}else{
			entityManager.merge(user);
		}
	}

    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }
}
