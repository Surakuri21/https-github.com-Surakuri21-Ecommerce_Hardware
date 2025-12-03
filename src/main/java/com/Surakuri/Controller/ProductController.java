package com.Surakuri.Controller;

import com.Surakuri.Model.dto.CreateProductRequest;
import com.Surakuri.Model.entity.Other_Business_Entities.Seller;
import com.Surakuri.Model.entity.Products_Categories.Product;
import com.Surakuri.Service.ProductService;
import com.Surakuri.Service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SellerService sellerService;

    // URL: POST http://localhost:2121/api/products/seller/{sellerId}
    @PostMapping("/seller/{sellerId}")
    public ResponseEntity<Product> addProduct(@PathVariable Long sellerId,
                                              @RequestBody CreateProductRequest req) {

        Product newProduct = productService.createProduct(req, sellerId);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }


    // URL: POST http://localhost:2121/api/products/create
    @PostMapping("/create")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req) {
        // We need to get the seller from the JWT
        Seller seller = sellerService.findSellerProfileByJwt();
        Product product = productService.createProduct(req, seller.getId());
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
}