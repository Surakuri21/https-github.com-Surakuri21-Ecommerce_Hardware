package com.Surakuri.Service;

import com.Surakuri.Model.entity.Products_Categories.Product;
import com.Surakuri.Repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service // Marks this class as a Spring service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    // Constructor injection
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ------------------------------------------------------------
    // Save or update a product
    // Returns the saved Product (with ID if new)
    // ------------------------------------------------------------
    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // ------------------------------------------------------------
    // Find a product by its unique ID
    // Returns Optional<Product> (empty if not found)
    // ------------------------------------------------------------
    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // ------------------------------------------------------------
    // Retrieve all products
    // Returns a list (empty if no products exist)
    // ------------------------------------------------------------
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // ------------------------------------------------------------
    // Delete a product by its ID
    // ------------------------------------------------------------
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // ------------------------------------------------------------
    // Get all products under a specific category name
    // Returns list (empty if none)
    // ------------------------------------------------------------
    @Override
    public List<Product> getProductsByCategory(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    // ------------------------------------------------------------
    // Get all products under a specific category ID
    // Returns list (empty if none)
    // ------------------------------------------------------------
    @Override
    public List<Product> getByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    // ------------------------------------------------------------
    // Search products by keyword in name (case-insensitive)
    // Returns list (empty if none)
    // ------------------------------------------------------------
    @Override
    public List<Product> getByNameContainingIgnoreCase(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    // ------------------------------------------------------------
    // Find a product by exact name
    // Returns Optional<Product> (empty if not found)
    // ------------------------------------------------------------
    @Override
    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }
}
