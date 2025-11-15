package com.Surakuri.Repository;

import com.Surakuri.Model.entity.Other_Business_Entities.SellerReport;  // Adjust package if different
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SellerReportRepository extends JpaRepository<SellerReport, Long> {
    List<SellerReport> findBySellerId(Long sellerId);  // Example custom query
}
