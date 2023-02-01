package com.example.kaushoporder.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    PROCESSING("주문처리중"),
    DONE("주문완료"),
    FAILED("주문실패");

    private String status;
}
