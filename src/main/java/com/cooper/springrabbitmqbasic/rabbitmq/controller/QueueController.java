package com.cooper.springrabbitmqbasic.rabbitmq.controller;

import com.cooper.springrabbitmqbasic.rabbitmq.dto.QueueCreateRequestDto;
import com.cooper.springrabbitmqbasic.rabbitmq.dto.QueueDeletedResponseDto;
import com.cooper.springrabbitmqbasic.rabbitmq.service.QueueService;
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
@RequestMapping("/api/v1/amqp/queues")
@RequiredArgsConstructor
public class QueueController {

    private final QueueService queueService;

    @PostMapping
    public ResponseEntity<String> createQueue(@RequestBody QueueCreateRequestDto queueCreateRequest) {
        String createdQueueName = queueService.createQueue(queueCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdQueueName);
    }

    @DeleteMapping("/{queueName}")
    public ResponseEntity<QueueDeletedResponseDto> deleteQueue(@PathVariable String queueName) {
        QueueDeletedResponseDto queueDeletedResponseDto = queueService.deleteQueue(queueName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(queueDeletedResponseDto);
    }

}
