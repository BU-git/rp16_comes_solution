package com.bionic.dao;

import com.bionic.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author Dima Budko
 */
@Repository
public interface RideDao extends JpaRepository<Ride, Integer> {
    @Query("select s.rides from Shift s where s.id =:shift_id")
    List<Ride> getByShiftId(@Param("shift_id") int shift_id);
}
