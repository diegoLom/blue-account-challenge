package com.losolved.nasa.services.impl;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.losolved.nasa.dto.PositionDTO;
import com.losolved.nasa.dto.ResponseDTO;
import com.losolved.nasa.errorhandling.NoSuchRobotException;
import com.losolved.nasa.model.Robot;
import com.losolved.nasa.repositories.RobotRepository;
import com.losolved.nasa.services.QueueCreationService;
import com.losolved.nasa.services.RobotService;

@Service
public class RobotServiceImpl implements RobotService {
	
	@Autowired
	private RobotRepository robotRepository; 
	
	@Autowired
	private QueueCreationService queueCreationService; 

	@Override
	public ResponseDTO launch(Robot robot) {
		// TODO Auto-generated method stub
		Robot robotCreated = robotRepository.save(robot);
		queueCreationService.createQueueAndBind(robotCreated.getId());
		return new ResponseDTO("Robot launched", HttpStatus.CREATED.value());
	}
	
	
	@Override
	public ResponseDTO updatePosition(PositionDTO positionDTO, UUID id) {
		// TODO Auto-generated method stub
		robotRepository.updatePosition(positionDTO.posX(), positionDTO.posY(), positionDTO.orientation(), id);
		
		return new ResponseDTO("Position updated", HttpStatus.OK.value());
	}

	@Override
	public PositionDTO getRobotPosition(UUID id) {
		// TODO Auto-generated method stub
		PositionDTO positionDTO = robotRepository.findPositionById(id);
		if(positionDTO != null) return positionDTO;
		throw new NoSuchRobotException();
		
	}
	

}
