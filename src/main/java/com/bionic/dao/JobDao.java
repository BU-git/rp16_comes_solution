/**
 * 
 */
package com.bionic.dao;

import java.util.List;

import com.bionic.model.Job;

/**
 * @author vitalii.levash
 *
 */
public interface JobDao {
 public List<Job> findAllJob();
 public void processJob(Job job);
}
