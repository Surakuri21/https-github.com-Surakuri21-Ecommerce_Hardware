package com.Surakuri.Repository;

import com.Surakuri.Model.entity.Products_Categories.Deal;  // Adjust package if different
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal, Long> {
    // Add custom queries if needed
}
