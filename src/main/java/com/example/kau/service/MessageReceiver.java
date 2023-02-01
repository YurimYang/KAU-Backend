package com.example.kau.service;

import com.example.kau.model.StockMessageDto;
import com.example.kau.model.TransactionPhase;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.sql.rowset.spi.TransactionalWriter;

@Slf4j
@Service
@RequiredArgsConstructor

public class MessageReceiver {
    private final ProductService productService;
    private final MessageSender messageSender;
    private final ObjectMapper mapper;

    @SqsListener(value = "2021125035-stock-queue", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveStringMessage(@Payload String payload,
                                     @Header("tid") String tid,
                                     @Header("Phase") String phase) throws Exception {
        if (phase.equals(TransactionPhase.TRY.getType())) {
            StockMessageDto stockMessage = mapper.readValue(payload, StockMessageDto.class);
            boolean changeResult = productService.addStock(stockMessage.getProductId(), stockMessage.getStockChanged());

            try {
                if (!changeResult) {
                    messageSender.sendAck(tid, TransactionPhase.CANCEL);
                } else {
                    messageSender.sendAck(tid, TransactionPhase.CONFIRM);
                }
            } catch (Exception e) {
                productService.addStock(stockMessage.getProductId(), stockMessage.getStockChanged() * -1);
            }

        }
    }
}
