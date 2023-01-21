package com.example.kaushoporder.model;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private String customerName;
    private ProductDto product;
}
