package com.bionic.dao;

import com.bionic.model.DayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * author taras.yaroshchuk
 */
public interface DayTypeDao extends JpaRepository<DayType, Integer> {

    @Query("select dt from DayType dt where dt.user.id =:user_id and " +
            "(:date BETWEEN dt.startTime and dt.endTime)")
    DayType getDayType(@Param("user_id") int user_id, @Param("date") Date date);

    @Query("select dt from DayType dt where dt.user.id =:user_id and " +
            "dt.startTime >= :startDate and dt.endTime <= :endDate")
    List<DayType> getDayTypesForPeriod(@Param("user_id") int user_id, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
