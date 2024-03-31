package com.losolved.nasa.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class PublishMessageService {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void sendMessage(String message) {
		
	}

}
