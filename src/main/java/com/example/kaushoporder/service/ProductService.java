package com.example.kaushoporder.service;

import com.example.kaushoporder.model.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductService {
    @GetMapping("/api/v1/product/{productId}")
    ProductDto getProduct(@PathVariable("productId") Long productId);
}
