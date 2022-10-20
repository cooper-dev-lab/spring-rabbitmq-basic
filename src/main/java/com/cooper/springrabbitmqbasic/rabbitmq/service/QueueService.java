package com.cooper.springrabbitmqbasic.rabbitmq.service;

import com.cooper.springrabbitmqbasic.rabbitmq.dto.QueueCreateRequestDto;
import com.cooper.springrabbitmqbasic.rabbitmq.dto.QueueDeletedResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueService {

    private final AmqpAdmin amqpAdmin;

    public String createQueue(QueueCreateRequestDto queueCreateRequest) {
        return amqpAdmin.declareQueue(new Queue(queueCreateRequest.getQueueName()));
    }

    public QueueDeletedResponseDto deleteQueue(String queueName) {
        boolean queueDeleted = amqpAdmin.deleteQueue(queueName);
        return QueueDeletedResponseDto.create(queueName, queueDeleted);
    }

}
