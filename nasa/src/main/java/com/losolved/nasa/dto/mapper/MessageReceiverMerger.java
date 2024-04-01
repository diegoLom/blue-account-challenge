package com.losolved.nasa.dto.mapper;

import com.losolved.nasa.dto.MessageReceiveDTO;
import com.losolved.nasa.dto.PositionDTO;
import com.losolved.nasa.model.Register;
import com.losolved.nasa.model.Robot;


//TODO: It should be tested due to the operation/conversion. It isn't thrid party library. 
public class MessageReceiverMerger {
	
	public static PositionDTO positionFromMessage(MessageReceiveDTO message) {
		return new PositionDTO(message.posX(), message.posY(), message.orientation());
	}
      
	
	public static Register registerFromMessage(MessageReceiveDTO message) {
		Robot robot = Robot.builder().id(message.id()).build();
		return Register.builder().register(message.register()).registerDate(message.date()).robot(robot).build(); //(message.posX(), message.posY(), message.orientation());
	}

}
