package com.Surakuri.Repository;

import com.Surakuri.Model.entity.Other_Business_Entities.Verification;  // Adjust package if different
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
    Optional<Verification> findByEmail(String email);
    Optional<Verification> findByUserId(Long userId);
    Optional<Verification> findBySellerId(Long sellerId);
}
