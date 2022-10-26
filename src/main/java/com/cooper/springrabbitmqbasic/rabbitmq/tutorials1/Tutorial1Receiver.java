package com.cooper.springrabbitmqbasic.rabbitmq.tutorials1;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "hello")
public class Tutorial1Receiver {

    @RabbitHandler
    public void receive(String message) {
        System.out.println("[x] Received : " + message);
    }

}
