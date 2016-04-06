package com.bionic.dao;

import com.bionic.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * author Dima Budko
 * v.0.1
 */
public interface ShiftDao extends JpaRepository<Shift,Integer>{
    @Query("select u.shifts from User u where u.id =:user_id")
    List<Shift> getByUserId(@Param("user_id") int user_id);
}
