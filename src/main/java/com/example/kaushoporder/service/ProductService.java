package com.example.kaushoporder.service;

import com.example.kaushoporder.model.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ProductService", url = "http://localhost:8080/api/v1")
public interface ProductService {

    @GetMapping("/product/{productId}")
    ProductDto getProduct(@PathVariable("productId") Long productId);
}
