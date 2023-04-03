package com.example.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SenderController {

    Logger logger = LoggerFactory.getLogger(SenderController.class);

    private final RabbitTemplate template;
    //    private final FanoutExchange exchange;
//    private final DirectExchange directExchange;
    private final TopicExchange topicExchange;

    @Autowired
    public SenderController(
            RabbitTemplate template,
          /*FanoutExchange fanoutExchange
            DirectExchange directExchange*/
            TopicExchange topicExchange) {
        this.template = template;
        this.topicExchange = topicExchange;
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody Map<String, String> messageMap) {
        logger.info("Publish in myQ - ");
        template.setExchange(topicExchange.getName());
        template.convertAndSend(messageMap.get("key"), messageMap.get("message"));
        return ResponseEntity.ok("Success publish to queue");
    }
}
