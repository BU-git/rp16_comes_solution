/**
 * 
 */
package com.bionic.dao;

import com.bionic.model.Job;

import java.util.List;

/**
 * @author vitalii.levash
 *
 */
public interface JobDao {
    List<Job> findAllJob();
    void processJob(Job job);
}
