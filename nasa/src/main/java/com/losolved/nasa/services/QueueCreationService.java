package com.losolved.nasa.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.losolved.nasa.configuration.DynamicQueueConfiguration;

@Component
public class QueueCreationService {

    private final DynamicQueueConfiguration dynamicQueueConfiguration;
    
    private static final String NAME_SUFFIX = "navigation"; 
    
    private static final String ROUNTING_KEY = "nav"; 

    @Autowired
    public QueueCreationService(DynamicQueueConfiguration dynamicQueueConfiguration) {
        this.dynamicQueueConfiguration = dynamicQueueConfiguration;
    }

    public void createQueueAndBind(String queueName, String routingKey) {
        dynamicQueueConfiguration.declareQueueAndBind(queueName, routingKey);
    }
    
    public void createQueueAndBind(UUID id) {
        dynamicQueueConfiguration.declareQueueAndBind(getQueueName(id), getRoutingKey(id));
    }
    
    
    public static String getQueueName(UUID id) {
    	return id + "_" + NAME_SUFFIX;
    }
    
    public static String getRoutingKey(UUID id) {
    	return id + "_" + NAME_SUFFIX;
    }
}
