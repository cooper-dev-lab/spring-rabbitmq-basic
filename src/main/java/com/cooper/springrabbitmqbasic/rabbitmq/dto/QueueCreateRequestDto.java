package com.cooper.springrabbitmqbasic.rabbitmq.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QueueCreateRequestDto {

    private String queueName;

}
