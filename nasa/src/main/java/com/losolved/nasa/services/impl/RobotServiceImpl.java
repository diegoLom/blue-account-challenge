package com.losolved.nasa.services.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.losolved.nasa.dto.PositionDTO;
import com.losolved.nasa.dto.ResponseDTO;
import com.losolved.nasa.model.Robot;
import com.losolved.nasa.repositories.RobotRepository;
import com.losolved.nasa.services.RobotService;

public class RobotServiceImpl implements RobotService {
	
	@Autowired
	private RobotRepository robotRepository; 

	@Override
	public ResponseDTO launch(Robot robot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseDTO updatePosition(PositionDTO positionDTO, UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PositionDTO getRobotPosition(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

}
