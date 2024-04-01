package com.losolved.nasa.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.losolved.nasa.dto.PositionDTO;
import com.losolved.nasa.model.Robot;

import jakarta.transaction.Transactional;

@Repository
public interface RobotRepository extends CrudRepository<Robot, UUID> {

	@Modifying
    @Transactional
    @Query("UPDATE Robot r SET r.posX = :posX, r.posY = :posY, r.orientation = :orientation WHERE r.id = :id")
    void updatePosition(@Param("posX") int posX, @Param("posY") int posY, @Param("orientation") char orientation, @Param("id") UUID id);
    
    @Query("SELECT new com.losolved.nasa.dto.PositionDTO(r.posX, r.posY, r.orientation) FROM Robot r WHERE r.id = ?1")
    PositionDTO findPositionById(UUID id);

}
