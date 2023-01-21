package com.example.kaushoporder.service;

import com.example.kaushoporder.entity.Order;
import com.example.kaushoporder.model.OrderDto;
import com.example.kaushoporder.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
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
        return orderRepository.save(product);
    }
}
