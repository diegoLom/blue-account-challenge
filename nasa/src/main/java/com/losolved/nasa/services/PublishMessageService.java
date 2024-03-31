package com.losolved.nasa.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.losolved.nasa.dto.MessageQueue;
import com.losolved.nasa.dto.ResponseDTO;

@Component
public class PublishMessageService {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public ResponseDTO sendMessage(MessageQueue message) {
		rabbitTemplate.convertSendAndReceive(null);
		
		return new ResponseDTO("Published successfully", HttpStatus.OK.value());
	}

}
