/**
 * 
 */
package com.bionic.dao;

import com.bionic.model.Employer;
import org.springframework.data.repository.CrudRepository;

/**
 * @author vitalii.levash
 *
 */
public interface EmployerDao extends CrudRepository<Employer, Integer>  {

}
