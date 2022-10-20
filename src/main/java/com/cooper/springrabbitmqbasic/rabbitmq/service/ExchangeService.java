package com.cooper.springrabbitmqbasic.rabbitmq.service;

import com.cooper.springrabbitmqbasic.rabbitmq.dto.ExchangeCreateRequestDto;
import com.cooper.springrabbitmqbasic.rabbitmq.dto.ExchangeDeletedResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final AmqpAdmin amqpAdmin;

    public String createExchange(ExchangeCreateRequestDto exchangeCreateRequestDto) {
        Exchange createdExchangeName = new TopicExchange(exchangeCreateRequestDto.getExchangeName());
        amqpAdmin.declareExchange(createdExchangeName);
        return createdExchangeName.getName();
    }

    public ExchangeDeletedResponseDto deleteExchange(String exchangeName) {
        boolean exchangeDeleted = amqpAdmin.deleteExchange(exchangeName);
        return ExchangeDeletedResponseDto.create(exchangeName, exchangeDeleted);
    }

}
