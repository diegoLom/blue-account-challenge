package com.losolved.nasa.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.losolved.nasa.dto.PositionDTO;
import com.losolved.nasa.dto.ResponseDTO;
import com.losolved.nasa.model.Robot;


public interface RobotService {
	
	public ResponseDTO launch(Robot robot);
	public ResponseDTO updatePosition(PositionDTO positionDTO, UUID id); 
	public PositionDTO getRobotPosition(UUID id); 
	
}
