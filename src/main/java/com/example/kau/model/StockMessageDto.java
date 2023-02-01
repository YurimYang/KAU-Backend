package com.example.kau.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class StockMessageDto {
    Long productId;
    Integer stockChanged = 0;
}
