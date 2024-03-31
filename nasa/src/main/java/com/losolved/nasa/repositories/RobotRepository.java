package com.losolved.nasa.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.losolved.nasa.model.Robot;

public interface RobotRepository extends CrudRepository<Robot, UUID> {

}
