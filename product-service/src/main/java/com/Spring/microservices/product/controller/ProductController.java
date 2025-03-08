package com.Spring.microservices.product.controller;

import com.Spring.microservices.product.dto.ProductRequest;
import com.Spring.microservices.product.dto.ProductResponse;
import com.Spring.microservices.product.model.Product;
import com.Spring.microservices.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest){
       return productService.createProduct(productRequest);

    }

}
