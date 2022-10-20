package com.cooper.springrabbitmqbasic.rabbitmq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExchangeDeletedResponseDto {

    private String exchangeName;
    private boolean exchangeDeleted;

    public static ExchangeDeletedResponseDto create(String exchangeName, boolean exchangeDeleted) {
        return new ExchangeDeletedResponseDto(exchangeName, exchangeDeleted);
    }

}
