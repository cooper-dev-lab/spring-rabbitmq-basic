package com.cooper.springrabbitmqbasic.rabbitmq.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BindingCreateRequestDto {

    private String queueName;
    private String exchangeName;

}
