package com.bionic.dao;

import com.bionic.model.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * Created by Kliakhin on 03/17/16.
 */
public interface WorkScheduleDao extends JpaRepository<WorkSchedule, Integer>{

    @Query("select u.workSchedule from User u where u.id =:user_id")
    WorkSchedule findByUserId(@Param("user_id") int user_id);

    //TODO idk if it works
    @Query("select ws from WorkSchedule ws where ws.user.id =:user_id and " +
            "(:date BETWEEN ws.creationTime and ws.deactivationTime)")
    WorkSchedule getActualWorkSchedule(@Param("user_id")  int user_id, @Param("date") Date date);
}
