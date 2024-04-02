package com.losolved.robot.configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.losolved.robot.RobotApplication;

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
   
    @Value("${robot.uuid.parameter}")
    private String ID_APPLICATION;
    
    public final String QUEUE_NAME = ID_APPLICATION+"_navigation";
    public static final String EXCHANGE_NAME = "nasa";
    public final String ROUTING_KEY = ID_APPLICATION+"_nav";

    
    @Bean
    public Queue registrationQueue() {
        return new Queue(ID_APPLICATION+"_navigation");
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(registrationQueue()).to(directExchange()).with(ID_APPLICATION+"_nav");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }
    
    
}
