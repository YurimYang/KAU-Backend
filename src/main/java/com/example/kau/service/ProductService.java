package com.example.kau.service;

import com.example.kau.entity.Product;
import com.example.kau.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.procedure.ProcedureOutputs;
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

    public boolean addStock(long productId, int stockChanged) {
        Optional<Product> optProduct = productRepository.findById(productId);
        if(optProduct.isEmpty()) {
            return false;
        }

        Product product = optProduct.get();
        if(product.getStock() + stockChanged < 0) {
            return false;
        }
        product.setStock(product.getStock() + stockChanged);

        try {
            productRepository.save(product);
            return true;
        }catch(Exception e) {
            return false;
        }
    }
}
