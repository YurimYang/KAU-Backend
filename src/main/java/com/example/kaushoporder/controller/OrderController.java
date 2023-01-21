package com.example.kaushoporder.controller;

import com.example.kaushoporder.entity.Order;
import com.example.kaushoporder.model.OrderDto;
import com.example.kaushoporder.model.OrderDto;
import com.example.kaushoporder.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/v1/order/{orderId}")
    public ResponseEntity<OrderDto> getOrder(
            @PathVariable("orderId") long orderId
    ) {
        Optional<OrderDto> optOrderDto = orderService.getOrder(orderId);

        if(optOrderDto.isPresent()) {
            return ResponseEntity.ok(optOrderDto.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/v1/orders")
    public ResponseEntity<Iterable<Order>> getOrders() {
        Iterable<Order> orders = orderService.getOrders();

        return ResponseEntity.ok(orders);
    }

    @PostMapping("/v1/order")
    public ResponseEntity createOrder(
            @RequestBody Order order
    ) {
        order.setId(null);

        Order newOrder = orderService.createOrder(order);
        URI newProductUri = URI.create("/api/v1/order/" + newOrder.getId());

        return ResponseEntity.created(newProductUri).build();
    }

}
