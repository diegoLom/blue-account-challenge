package com.losolved.nasa.errorhandling;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class NoSuchRobotException extends RuntimeException {
	public NoSuchRobotException() {
		super("Robot not found");
		
	}
	
	public NoSuchRobotException(String message) {
		super(message);
	
	}

}
