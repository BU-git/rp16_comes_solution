package com.bionic.dao;

import com.bionic.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author vitalii.levash
 * @Author Pavel Boiko
 */
@Repository
public interface JobDao extends JpaRepository<Job, Integer> {

    @Query("select j from Job j where j.id = :id")
    Job findById(@Param("id") Integer id);

}
