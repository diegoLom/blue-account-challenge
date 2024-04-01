package com.losolved.nasa.errorhandling;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class NoSuchRegisterException extends RuntimeException {
	
	public NoSuchRegisterException() {
		super("Register not found");
		
	}
	
	public NoSuchRegisterException(String message) {
		super(message);
	
	}
}
