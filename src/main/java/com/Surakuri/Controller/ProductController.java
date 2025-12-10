package com.Surakuri.Controller;

import com.Surakuri.Model.dto.CreateProductRequest;
import com.Surakuri.Model.entity.Other_Business_Entities.Seller;
import com.Surakuri.Model.entity.Products_Categories.Product;
import com.Surakuri.Service.ProductService;
import com.Surakuri.Service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * Endpoint to create a new product. This is protected and only accessible by authenticated sellers.
     * The seller is identified via their JWT token.
     * URL: POST http://localhost:2121/api/products/create
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req) {
        Seller seller = sellerService.findSellerProfileByJwt();
        Product product = productService.createProduct(req, seller.getId());
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    /**
     * Public endpoint to get a paginated list of all active products.
     * Supports sorting and pagination via URL parameters.
     * Example: GET http://localhost:2121/api/products?page=0&size=10&sort=price,asc
     * @param pageable A Pageable object automatically created by Spring from the request parameters.
     * @return A ResponseEntity containing a Page of products.
     */
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        Page<Product> products = productService.findAllProducts(pageable);
        return ResponseEntity.ok(products);
    }
}