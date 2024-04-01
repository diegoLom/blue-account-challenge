package com.losolved.nasa.services;

import java.util.List;
import java.util.UUID;

import com.losolved.nasa.dto.InDateBetweenAndRobot;
import com.losolved.nasa.dto.ResponseDTO;
import com.losolved.nasa.model.Register;

public interface RegisterService {
	
	public ResponseDTO writeDown(Register register); 
	public List<Register> getRegisterFromRobo(UUID id); 
	public List<Register> getRegisterFromDateBetween(InDateBetweenAndRobot inDateBetweenAndRobot); 

}
