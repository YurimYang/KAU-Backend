package com.example.kau.service;

import com.example.kau.entity.Product;
import com.example.kau.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Optional<Product> getProduct(Long id){
        return productRepository.findById(id);
    }

    public Product createProduct (Product product) {
        return productRepository.save(product);
    }
}
