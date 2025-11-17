package com.Surakuri.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.Optional;
import java.util.List;

import com.Surakuri.Model.entity.Products_Categories.Product;
import com.Surakuri.Service.ProductService;

// Custom exception for 404 Not Found
@ResponseStatus(HttpStatus.NOT_FOUND)
class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}

@RestController
@RequestMapping("/products")   // Base URL for all product endpoints
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ------------------------------------------------------------
    // Helper method: converts Optional<T> into ResponseEntity
    // If value exists → 200 OK
    // If empty → throws ResourceNotFoundException (mapped to 404)
    // ------------------------------------------------------------
    private <T> ResponseEntity<T> mapOrNotFound(Optional<T> optional, String message) {
        return optional.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(message));
    }

    // ------------------------------------------------------------
    // GET /products/{id} → returns 404 if not found
    // ------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return mapOrNotFound(productService.getProductById(id),
                "Product with ID " + id + " not found");
    }

    // ------------------------------------------------------------
    // GET /products/name/{name} → returns 404 if not found
    // ------------------------------------------------------------
    @GetMapping("/name/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        return mapOrNotFound(productService.getProductByName(name),
                "Product with name '" + name + "' not found");
    }

    // ------------------------------------------------------------
    // GET /products/category/{categoryName} → returns 204 if empty
    // ------------------------------------------------------------
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String categoryName) {
        List<Product> products = productService.getProductsByCategory(categoryName);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    // ------------------------------------------------------------
    // GET /products/search?keyword=... → returns 204 if no matches
    // Search products by name containing keyword (case-insensitive)
    // ------------------------------------------------------------
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products = productService.getByNameContainingIgnoreCase(keyword);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    // ------------------------------------------------------------
    // GET /products/categoryId/{categoryId} → returns 204 if empty
    // Fetch products by category ID
    // ------------------------------------------------------------
    @GetMapping("/categoryId/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategoryId(@PathVariable Long categoryId) {
        List<Product> products = productService.getByCategoryId(categoryId);
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    // ------------------------------------------------------------
    // POST /products → create/save a product
    // Returns 200 OK with saved product
    // ------------------------------------------------------------
    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        Product saved = productService.saveProduct(product);
        return ResponseEntity.ok(saved);
    }

    // ------------------------------------------------------------
    // DELETE /products/{id} → delete a product by ID
    // Returns 200 OK if deleted
    // ------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
