package com.Surakuri.Controller;

import com.Surakuri.Model.dto.CreateProductRequest;
import com.Surakuri.Model.entity.Products_Categories.Product;
import com.Surakuri.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // URL: POST http://localhost:2121/api/products/seller/{sellerId}
    @PostMapping("/seller/{sellerId}")
    public ResponseEntity<Product> addProduct(@PathVariable Long sellerId,
                                              @RequestBody CreateProductRequest req) {

        Product newProduct = productService.createProduct(req, sellerId);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }
}