package com.bionic.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * @author vitalii.levash
 */
@Entity
@Table(name = "jobs")
public class Job {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "jobId")
	private Integer id;
	private String jobName;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "jobs")
	@JsonIgnore
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

	@Override
	public String toString() {
        return "Job{" +
                "id=" + id +
                ", jobName=" + jobName +
                "}";
    }
}
