package com.losolved.nasa.services.impl;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.losolved.nasa.dto.PositionDTO;
import com.losolved.nasa.dto.ResponseDTO;
import com.losolved.nasa.errorhandling.NoSuchRobotException;
import com.losolved.nasa.model.Robot;
import com.losolved.nasa.repositories.RobotRepository;
import com.losolved.nasa.services.RobotService;

public class RobotServiceImpl implements RobotService {
	
	@Autowired
	private RobotRepository robotRepository; 

	@Override
	public ResponseDTO launch(Robot robot) {
		// TODO Auto-generated method stub
		
		robotRepository.save(robot);
		return new ResponseDTO("Robot launched", HttpStatus.CREATED.value());
	}
	
	
	@Override
	public ResponseDTO updatePosition(PositionDTO positionDTO, UUID id) {
		// TODO Auto-generated method stub
		Robot robot = getRobot(id);
		robot.setPosX(positionDTO.posX());
		robot.setPosY(positionDTO.posY());
		robot.setOrientation(positionDTO.orientation());
		
		robotRepository.save(robot);
		
		return new ResponseDTO("Robot updated", HttpStatus.OK.value());
	}

	@Override
	public PositionDTO getRobotPosition(UUID id) {
		// TODO Auto-generated method stub
		Robot robot = getRobot(id);
		
		PositionDTO positionDTO = new PositionDTO(robot.getPosX(), robot.getPosY(), robot.getOrientation());
		
		return positionDTO;
	}
	
	private Robot getRobot(UUID id) {
		return robotRepository.findById(id).orElseThrow(() -> new NoSuchRobotException()); 
	}

}
