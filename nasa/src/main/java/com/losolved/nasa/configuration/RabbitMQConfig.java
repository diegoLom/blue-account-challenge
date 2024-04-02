package com.losolved.nasa.configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.UUID;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;


@Configuration
public class RabbitMQConfig {

    private final ConnectionFactory connectionFactory;
    
    @Autowired
    public RabbitMQConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory);
    }
    
    public static final String QUEUE_NAME = "REGISTER_READER";
    public static final String EXCHANGE_NAME = "nasa";
    public static final String ROUTING_KEY = "REGISTER_READER_nasa";

    
    @Bean
    public Queue registrationQueue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(registrationQueue()).to(directExchange()).with(ROUTING_KEY);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }
    
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
    	Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper());
    	
    	
           return converter;
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        
        SimpleModule module = new SimpleModule();
        module.addDeserializer(UUID.class, new UUIDFromStringDeserializer());
        objectMapper.registerModule(module);
        
        SimpleModule module2= new SimpleModule();
        module.addDeserializer(byte[].class, new ByteArrayDeserializer());
        objectMapper.registerModule(module);

        return objectMapper;
    }
    
    static class UUIDFromStringDeserializer extends com.fasterxml.jackson.databind.JsonDeserializer<UUID> {
        @Override
        public UUID deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws java.io.IOException, com.fasterxml.jackson.core.JsonProcessingException {
            return UUID.fromString(p.getValueAsString());
        }
    }
}
