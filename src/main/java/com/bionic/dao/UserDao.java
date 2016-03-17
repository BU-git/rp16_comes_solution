package com.bionic.dao;

import com.bionic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author vitalii.levash
 * @version 0.2
 */
@Repository
public interface UserDao  extends JpaRepository<User, Integer> {

     @Query("select b from User b where b.email = :name")
     User findByName(@Param("name") String name);
//    public User findByUsername(String username);
//    public void processUser(User user);
//
//    User findById(int id);
}
