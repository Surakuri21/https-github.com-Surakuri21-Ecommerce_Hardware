package com.Surakuri.Repository;

import com.Surakuri.Model.entity.Products_Categories.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // ==========================================
    // 1. STOCK CHECKS
    // ==========================================

    // Find inventory for a specific variant SKU ID
    // Usage: When adding to cart, check if stock exists.
    Optional<Inventory> findByVariantId(Long variantId);

    // Find by SKU String (Useful for barcode scanners)
    Optional<Inventory> findByVariantSku(String sku);


    // ==========================================
    // 2. ADMIN / DASHBOARD ALERTS
    // ==========================================

    // Find all items where stock is below the minimum level
    // Usage: "Low Stock Alert" widget on Seller Dashboard
    @Query("SELECT i FROM Inventory i WHERE i.quantityOnHand <= i.minStockLevel")
    List<Inventory> findLowStockItems();

    // Find out-of-stock items (Zero quantity)
    List<Inventory> findByQuantityOnHandLessThanEqual(int quantity);
}