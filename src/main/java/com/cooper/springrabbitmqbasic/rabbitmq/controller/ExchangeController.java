package com.cooper.springrabbitmqbasic.rabbitmq.controller;

import com.cooper.springrabbitmqbasic.rabbitmq.dto.ExchangeCreateRequestDto;
import com.cooper.springrabbitmqbasic.rabbitmq.dto.ExchangeDeletedResponseDto;
import com.cooper.springrabbitmqbasic.rabbitmq.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/amqp/exchanges")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping
    public ResponseEntity<String> createExchange(@RequestBody ExchangeCreateRequestDto exchangeCreateRequestDto) {
        String createdExchangeName = exchangeService.createExchange(exchangeCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdExchangeName);
    }

    @DeleteMapping("/{exchangeName}")
    public ResponseEntity<ExchangeDeletedResponseDto> deleteExchange(@PathVariable String exchangeName) {
        ExchangeDeletedResponseDto exchangeDeletedResponseDto = exchangeService.deleteExchange(exchangeName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(exchangeDeletedResponseDto);
    }

}
