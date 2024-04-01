package com.losolved.nasa.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.losolved.nasa.model.Register;
import com.losolved.nasa.model.Robot;

@Repository
public interface RegisterRepository extends CrudRepository<Register, Long> {
	
	List<Register> findByRobot(Robot robot);
	
	List<Register> findByRegisterDateBetweenAndRobot(LocalDateTime startRegisterDate, LocalDateTime endDeparture,Robot robot);

}