package com.losolved.robot.services;

import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublishMessageService {

	private final RabbitTemplate rabbitTemplate;

	public static final String QUEUE_NAME = "REGISTER_READER";
	public static final String EXCHANGE_NAME = "nasa";
	public static final String ROUTING_KEY = "REGISTER_READER_nasa";

	@Autowired
	public PublishMessageService(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void sendMessage(String content) {
		
		 Message rabbitMessage = MessageBuilder.withBody(content.getBytes())
	                .setContentType("application/json")
	                .build();

	        // Publish message to the specified queue
	     
	        
		rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, rabbitMessage);
	}
}