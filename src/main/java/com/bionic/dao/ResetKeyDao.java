package com.bionic.dao;

import com.bionic.model.ResetKey;
import com.bionic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * author Dima Budko
 * v.0.1
 */
public interface ResetKeyDao extends JpaRepository<ResetKey,Integer> {
    @Query("select b from ResetKey b where b.secret = :secret")
    ResetKey findBySecret(@Param("secret") long secret);
}
