package com.bionic.dao;

import com.bionic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author vitalii.levash
 * @author Dima Budko
 */
@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    @Query("select b from User b where b.email = :name")
    User findByName(@Param("name") String name);

    @Query("select b from User b where b.email = :email")
    User findByEmail(@Param("email") String email);
}
