package com.bionic.dao;

import com.bionic.model.UserKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * author Dima Budko
 * v.0.1
 */
public interface UserKeyDao extends JpaRepository<UserKey, Integer> {
    @Query("select b from UserKey b where b.secret = :secret and b.keyType='reset_password'")
    UserKey findBySecretForResetPass(@Param("secret") long secret);

    @Query("select b from UserKey b where b.secret = :secret and b.keyType='verification'")
    UserKey findBySecretForVerification(@Param("secret") long secret);

    @Query("select b from UserKey b where b.secret = :secret and b.keyType='report'")
    UserKey findBySecretForReport(@Param("secret") long secret);

    @Modifying
    @Transactional
    @Query("delete from UserKey u where u.email = :user_email")
    void deleteByUserEmail(@Param("user_email") String userEmail);

}
