package com.bionic.dao;

import com.bionic.model.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Kliakhin on 03/17/16.
 */
public interface WorkScheduleDao extends JpaRepository<WorkSchedule, Integer>{

}
