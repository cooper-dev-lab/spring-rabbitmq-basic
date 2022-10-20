package com.cooper.springrabbitmqbasic.rabbitmq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueueDeletedResponseDto {

    private final String queueName;
    private final boolean queueDeleted;

    public static QueueDeletedResponseDto create(String queueName, boolean queueDeleted) {
        return new QueueDeletedResponseDto(queueName, queueDeleted);
    }

}
