package com.example.kau.service;

import com.example.kau.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    public Product getProduct(Long id){
        Product product = new Product();
        product.setId(id);
        product.setName("상품" + id);

        return product;
    }
}
