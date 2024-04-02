package com.losolved.robot.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.losolved.robot.configuration.RabbitMQConfig;
import com.losolved.robot.services.impl.ActionsServiceImpl;


@Component
public class MessageReceiverService {
		
	@Autowired
	private ActionsServiceImpl actionsService;
	
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveMessage(String message) {
    	actionsService.process(message);
    }
}

