package com.Surakuri.Service;

import com.Surakuri.Model.entity.Products_Categories.Product;
import java.util.List;
import java.util.Optional;


import com.Surakuri.Model.entity.Products_Categories.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    // ------------------------------------------------------------
    // Save a new product or update an existing one
    // Returns the saved Product object
    // ------------------------------------------------------------
    Product saveProduct(Product product);

    // ------------------------------------------------------------
    // Fetch a product by its unique ID
    // Returns Optional<Product>:
    //   - Non-empty if product exists
    //   - Empty if not found
    // ------------------------------------------------------------
    Optional<Product> getProductById(Long id);

    // ------------------------------------------------------------
    // Fetch a product by its name
    // Returns Optional<Product>:
    //   - Non-empty if product exists
    //   - Empty if not found
    // ------------------------------------------------------------
    Optional<Product> getProductByName(String name);

    // ------------------------------------------------------------
    // Retrieve all products in the database
    // Returns a list of Product objects
    // Returns an empty list if no products exist
    // ------------------------------------------------------------
    List<Product> getAllProducts();

    // ------------------------------------------------------------
    // Delete a product by its ID
    // Does not return a value
    // ------------------------------------------------------------
    void deleteProduct(Long id);

    // ------------------------------------------------------------
    // Retrieve products by category name
    // Returns a list of products that belong to the given category
    // Returns an empty list if none found
    // ------------------------------------------------------------
    List<Product> getProductsByCategory(String categoryName);

    // ------------------------------------------------------------
    // Search products by a keyword in their name (case-insensitive)
    // Returns a list of matching products
    // Returns an empty list if no matches found
    // ------------------------------------------------------------
    List<Product> getByNameContainingIgnoreCase(String keyword);

    // ------------------------------------------------------------
    // Retrieve products by category ID
    // Returns a list of products under the specified category ID
    // Returns an empty list if none found
    // ------------------------------------------------------------
    List<Product> getByCategoryId(Long categoryId);
}
