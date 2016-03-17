/**
 * 
 */
package com.bionic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author vitalii.levash
 * @version 0.1
 */
@Entity
@Table(name="employers")
public class Employer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="employerId")
	private Integer id;
	@Column(name="employerName")
	private String name;
	
	public Employer(){ }

	
	public Integer getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}
	

}
