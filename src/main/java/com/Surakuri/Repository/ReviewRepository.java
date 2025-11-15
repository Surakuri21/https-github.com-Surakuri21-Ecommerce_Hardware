package com.Surakuri.Repository;

import com.Surakuri.Model.entity.Other_Business_Entities.Review;  // Adjust package if different
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(Long productId);  // Example custom query
}
