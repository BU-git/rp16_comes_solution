/**
 * 
 */
package com.bionic.model;

import javax.persistence.*;
import java.util.List;

/**
 * @author vitalii.levash
 *
 */
@Entity
@Table(name = "jobs")
public class Job {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "jobId")
	private int id;
	private String jobName;



	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "jobs")
	private List<User> users;

	public Job() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
