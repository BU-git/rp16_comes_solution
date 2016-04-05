package com.bionic.dao;

import com.bionic.model.InputRide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * author Dima Budko
 */
@Repository
public interface InputRideDao extends JpaRepository<InputRide,Integer>{
}
