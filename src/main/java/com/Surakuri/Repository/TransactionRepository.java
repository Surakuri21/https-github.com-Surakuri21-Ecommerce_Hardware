package com.Surakuri.Repository;

import com.Surakuri.Model.entity.Other_Business_Entities.Transaction;  // Adjust package if different
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCustomerId(Long customerId);
    List<Transaction> findBySellerId(Long sellerId);
    List<Transaction> findByOrderId(Long orderId);
}
