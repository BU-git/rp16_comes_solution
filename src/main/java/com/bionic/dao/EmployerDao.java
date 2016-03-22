package com.bionic.dao;

import com.bionic.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author vitalii.levash
 */
public interface EmployerDao extends JpaRepository<Employer, Integer>  {

}
