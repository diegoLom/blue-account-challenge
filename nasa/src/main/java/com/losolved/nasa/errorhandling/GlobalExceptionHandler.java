package com.losolved.nasa.errorhandling;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.losolved.nasa.dto.ResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = NoSuchRobotException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody ResponseDTO handleFlightException(NoSuchRobotException ex) {
		return new ResponseDTO(ex.getMessage(), HttpStatus.NOT_FOUND.value());
	}
	
	
	@ExceptionHandler(value = NoSuchRegisterException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody ResponseDTO handleAccommodationException(NoSuchRegisterException ex) {
		return new ResponseDTO(ex.getMessage(), HttpStatus.NOT_FOUND.value());
	}

}

