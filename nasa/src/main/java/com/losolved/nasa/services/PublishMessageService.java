package com.losolved.nasa.services;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.losolved.nasa.configuration.DynamicQueueConfiguration;
import com.losolved.nasa.dto.ResponseDTO;

@Component
public class PublishMessageService {

	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public PublishMessageService(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public ResponseDTO sendMessage(UUID id, String content) {

		rabbitTemplate.convertAndSend(DynamicQueueConfiguration.existingExchangeName, QueueCreationService.getRoutingKey(id), content);

		return new ResponseDTO("Message sent to NASA Orbit", 200);
	}
}