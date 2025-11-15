package com.Surakuri.Repository;

import com.Surakuri.Model.entity.Other_Business_Entities.Seller;  // Adjust package if different
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    // Add custom queries if needed
}
