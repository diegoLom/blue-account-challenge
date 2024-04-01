package com.losolved.nasa.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.losolved.nasa.configuration.RabbitMQConfig;
import com.losolved.nasa.dto.MessageReceiveDTO;
import com.losolved.nasa.dto.PositionDTO;
import com.losolved.nasa.dto.mapper.MessageReceiverMerger;
import com.losolved.nasa.model.Register;
import com.losolved.nasa.services.impl.RobotServiceImpl;

@Component
public class MessageReceiverService {
	
	@Autowired
    private Jackson2JsonMessageConverter messageConverter;
	
	@Autowired
	private RobotServiceImpl robotService;
	
	@Autowired
	private RegisterService registerService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(MessageReceiveDTO messageReceived) {
    	PositionDTO positionDTO = MessageReceiverMerger.positionFromMessage(messageReceived);
    	Register register = MessageReceiverMerger.registerFromMessage(messageReceived);
    	
    	robotService.updatePosition(positionDTO, messageReceived.id());
    	registerService.writeDown(register);
    }
}

