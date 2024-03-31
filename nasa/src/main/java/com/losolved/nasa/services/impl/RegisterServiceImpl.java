package com.losolved.nasa.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.losolved.nasa.dto.InDateBetweenAndRobot;
import com.losolved.nasa.dto.ResponseDTO;
import com.losolved.nasa.errorhandling.NoSuchRobotException;
import com.losolved.nasa.model.Register;
import com.losolved.nasa.model.Robot;
import com.losolved.nasa.repositories.RegisterRepository;
import com.losolved.nasa.services.RegisterService;

@Service
public class RegisterServiceImpl implements RegisterService {
	
	
	@Autowired
	private RegisterRepository registerRepository;

	@Override
	public ResponseDTO writeDown(Register register) {
		// TODO Auto-generated method stub
		registerRepository.save(register);
		
		return new ResponseDTO("Register saved", HttpStatus.CREATED.value());
	}

	@Override
	public List<Register> getRegisterFromRobo(UUID id) {
		// TODO Auto-generated method stub
	   Robot filter =  Robot.builder().id(id).build();
	   List<Register> registers = registerRepository.findByRobot(filter); 
	
	   if(!registers.isEmpty()) return registers;
	   throw new NoSuchRobotException("Register not found");
	}

	@Override
	public List<Register> getRegisterFromDateBetween(InDateBetweenAndRobot arg0) {
		// TODO Auto-generated method stub
		
		List<Register> registers = registerRepository.findByRegisterDateBetweenAndRobot(arg0.startRegister(), arg0.endRegister(), arg0.robot());
		if(!registers.isEmpty()) return registers;
		throw new NoSuchRobotException("Register not found");
	}

}
