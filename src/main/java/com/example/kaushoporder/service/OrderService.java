package com.example.kaushoporder.service;

import com.example.kaushoporder.entity.Order;
import com.example.kaushoporder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Optional<Order> getOrder(Long id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order product) {
        return orderRepository.save(product);
    }
}
