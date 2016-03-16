/**
 * 
 */
package com.bionic.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bionic.model.Job;

/**
 * @author vitalii.levash
 *
 */
@Repository
public class JobDaoImpl implements JobDao {
	@PersistenceContext
	private EntityManager entityManager;

	public List<Job> findAllJob() {
		TypedQuery<Job> query = entityManager.createQuery("SELECT j FROM Job j",Job.class);
		return null;
	}

	public void processJob(Job job) {
		
	}

}
