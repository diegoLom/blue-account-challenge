package com.losolved.robot.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.losolved.robot.model.Position;

import jakarta.transaction.Transactional;

@Repository
public interface PositionRepository extends CrudRepository<Position, UUID> {


}
