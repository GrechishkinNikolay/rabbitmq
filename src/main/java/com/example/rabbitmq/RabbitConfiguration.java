package com.example.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    public final static String QUEUE_NAME_1 = "myQueue1";
    public final static String QUEUE_NAME_2 = "myQueue2";

    Logger logger = LoggerFactory.getLogger(RabbitConfiguration.class);

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue myQueue1() {
        return new Queue(QUEUE_NAME_1);
    }

    @Bean
    public Queue myQueue2() {
        return new Queue(QUEUE_NAME_2);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout-common-exchange");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct-exchange");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topic-exchange");
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(myQueue1()).to(topicExchange()).with("Topic-one.*");
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(myQueue2()).to(topicExchange()).with("Topic-two.*");
    }

    /*
    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(myQueue1()).to(directExchange()).with("error");
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder.bind(myQueue2()).to(directExchange()).with("warning");
    }

    @Bean
    public Binding binding3() {
        return BindingBuilder.bind(myQueue2()).to(directExchange()).with("info");
    }*/

/*    @Bean
    public SimpleMessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueues(myQueue());
        container.setMessageListener(message -> logger.info("Received from myQ: " + new String(message.getBody())));
        return container;
    }*/
}
