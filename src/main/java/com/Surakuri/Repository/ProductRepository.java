package com.Surakuri.Repository;

import com.Surakuri.Model.entity.Products_Categories.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Search by name containing keyword
    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByCategoryName(String categoryName);

    List<Product> findByCategoryId(Long categoryId);

    Optional<Product> findByName(String name);


}
