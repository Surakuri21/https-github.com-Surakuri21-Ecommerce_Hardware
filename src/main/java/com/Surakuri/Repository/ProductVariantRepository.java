package com.Surakuri.Repository;

import com.Surakuri.Model.entity.Products_Categories.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    // ==========================================
    // 1. DISPLAY LOGIC (Product Details Page)
    // ==========================================

    // Find all options (Sizes/Weights) for a specific Product
    // Usage: When User loads "Phelps Dodge Wire", show options: ["3.5mm", "5.5mm", "8.0mm"]
    List<ProductVariant> findByProductId(Long productId);


    // ==========================================
    // 2. INVENTORY & CART LOGIC
    // ==========================================

    // Find a specific variant by its Unique SKU Code
    // Usage: Barcode scanner in warehouse OR backend inventory check
    Optional<ProductVariant> findBySku(String sku);

    // Check if an SKU exists (To prevent duplicates when Admin adds new items)
    boolean existsBySku(String sku);
}