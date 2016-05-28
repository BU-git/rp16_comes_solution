package com.bionic.dao;

import com.bionic.model.DayType;
import com.bionic.model.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * author taras.yaroshchuk
 */
public interface DayTypeDao extends JpaRepository<DayType, Integer> {

    @Query("select dt from DayType dt where dt.user.id =:user_id and " +
            "(:date BETWEEN dt.startTime and dt.endTime)")
    DayType getDayType(@Param("user_id")  int user_id, @Param("date") Date date);

}
