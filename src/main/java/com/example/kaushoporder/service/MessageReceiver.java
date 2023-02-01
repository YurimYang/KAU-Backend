package com.example.kaushoporder.service;

import com.example.kaushoporder.model.OrderStatus;
import com.example.kaushoporder.model.TransactionPhase;
import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor

public class MessageReceiver {
    private final EventService eventService;
    private final OrderService orderService;

    @SqsListener(value = "202112505-stock-queue", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveStringMessage(@Header("tid") String tid,
                                     @Header("phase") String phase) {
        if(phase.equals(TransactionPhase.CONFIRM.getType())) {
            eventService.getEvent(tid).ifPresent(event -> {
                eventService.updateEventStatus(tid, TransactionPhase.CONFIRM);
                orderService.updateStatus(event.getOrderId(), OrderStatus.DONE);
            });
        } else if(phase.equals(TransactionPhase.CANCEL.getType())) {
            eventService.getEvent(tid).ifPresent(event -> {
                eventService.updateEventStatus(tid, TransactionPhase.CANCEL);
                orderService.updateStatus(event.getOrderId(), OrderStatus.FAILED);
            });
        }
    }
}
