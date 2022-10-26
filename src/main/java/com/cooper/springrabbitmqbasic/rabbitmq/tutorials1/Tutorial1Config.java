package com.cooper.springrabbitmqbasic.rabbitmq.tutorials1;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Profile({"tutorial1", "hello-world"})
public class Tutorial1Config {

    @Bean
    public Queue hello() {
        return new Queue("hello");
    }

    @Profile("receiver")
    @Bean
    public Tutorial1Receiver receiver() {
        return new Tutorial1Receiver();
    }

    @Profile("sender")
    public Tutorial1Sender sender() {
        return new Tutorial1Sender();
    }
}
