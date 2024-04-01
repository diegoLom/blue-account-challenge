package com.losolved.nasa.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicQueueConfiguration {

    private final RabbitAdmin rabbitAdmin;
    public static final String existingExchangeName = "nasa";

    @Autowired
    public DynamicQueueConfiguration(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    public void declareQueueAndBind(String queueName, String routingKey) {
        Queue queue = new Queue(queueName);
        rabbitAdmin.declareQueue(queue);

        Binding binding = BindingBuilder.bind(queue).to(new DirectExchange(existingExchangeName)).with(routingKey);
        rabbitAdmin.declareBinding(binding);
    }
}
