package com.example.kaushoporder.service;

import com.example.kaushoporder.entity.Order;
import com.example.kaushoporder.model.OrderDto;
import com.example.kaushoporder.model.OrderStatus;
import com.example.kaushoporder.model.StockMessageDto;
import com.example.kaushoporder.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final EventService eventService;
    private final MessageSender messageSender;
    private final ObjectMapper objectMapper;

    public Optional<OrderDto> getOrder(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);

        if(optionalOrder.isEmpty()) {
            return Optional.empty();
        }

        Order order = optionalOrder.get();
        OrderDto orderDto = objectMapper.convertValue(order, OrderDto.class);
        orderDto.setProduct(productService.getProduct(order.getProductId()));

        return Optional.of(orderDto);
    }

    public Iterable<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order product) {
        Order result = null;
        try{
            result = orderRepository.save(product);
            String eventUUID = messageSender.send(new StockMessageDto(result.getProductId(),-1));

            eventService.createEvent(eventUUID, result.getId());

        }catch(Exception e) {
            log.error("Failed to save order.");
        }
        return result;
    }

    public void updateStatus(Long orderId, OrderStatus status) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            orderRepository.save(order);
        });
    }
}
