package com.Surakuri.Repository;

import com.Surakuri.Model.entity.Products_Categories.Home_Category;  // Adjust package if different
import org.springframework.data.jpa.repository.JpaRepository;

public interface Home_CategoryRepository extends JpaRepository<Home_Category, Long> {
    // Add custom queries if needed
}
