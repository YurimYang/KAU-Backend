package com.example.kau.controller;

import com.example.kau.entity.Product;
import com.example.kau.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/v1/product/{productId}")
    public ResponseEntity<Product> getProduct(
            @PathVariable("productId") long productId
    ) {
        Optional<Product> optProduct = productService.getProduct(productId);

        if (optProduct.isPresent()) {
            return ResponseEntity.ok(optProduct.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/v1/product")
    public ResponseEntity createProduct(
            @RequestBody Product product
    ) {
        product.setId(null);
        Product newProduct = productService.createProduct(product);
        URI newProductUri = URI.create("/api/v1/product/" + newProduct.getId());
        return ResponseEntity.created(newProductUri).build();

    }
}
