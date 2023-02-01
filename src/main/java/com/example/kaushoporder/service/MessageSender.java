package com.example.kaushoporder.service;


import java.io.Serial;
import java.util.UUID;

import com.example.kaushoporder.model.StockMessageDto;
import com.example.kaushoporder.model.TransactionPhase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSender {
    private static final String QUEUE_NAME = "2021125035-stock-queue";
    private final QueueMessagingTemplate messagingTemplate;
    private final ObjectMapper mapper;

    public String send(StockMessageDto payload) {
        try {
            String eventUUID = UUID.randomUUID().toString();

            Message msg = MessageBuilder.withPayload(mapper.writeValueAsString(payload))
                    .setHeader("tid",eventUUID)
                    .setHeader("phase", TransactionPhase.TRY.getType())
                    .build();
            messagingTemplate.send(QUEUE_NAME, msg);
            log.info("Message sent - productId : {} , stockChanged : {}", payload.getProductId(), payload.getStockChanged());
            return eventUUID;
        } catch (JsonProcessingException e) {
            log.error("Serializing payload failed. ");
            return null;
        }
    }
}

