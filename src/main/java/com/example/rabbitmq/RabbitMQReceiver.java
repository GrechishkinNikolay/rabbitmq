package com.example.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class RabbitMQReceiver {
    Logger logger = LoggerFactory.getLogger(RabbitMQReceiver.class);

    @RabbitListener(queues = RabbitConfiguration.QUEUE_NAME_1)
    public void receiveMessage(String massage) {
        logger.info("Received from {} queue: {}, by 1", RabbitConfiguration.QUEUE_NAME_1, massage);
    }

    @RabbitListener(queues = RabbitConfiguration.QUEUE_NAME_2)
    public void receiveMessage2(String massage) {
        logger.info("Received from {} queue: {}, by 2", RabbitConfiguration.QUEUE_NAME_2, massage);
    }
}
