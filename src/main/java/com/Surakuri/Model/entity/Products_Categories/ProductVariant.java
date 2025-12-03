package com.Surakuri.Model.entity.Products_Categories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "product_variants") // Matches SQL table
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variant_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String sku; // Stock Keeping Unit (e.g., "CEM-40KG")

    @Column(name = "variant_name")
    private String variantName; // e.g., "40kg Bag" or "1/2 inch diameter"

    // PRICING & SPECS
    // Always use BigDecimal for money calculations to avoid rounding errors
    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "weight_kg")
    private BigDecimal weightKg; // Critical for calculating shipping of heavy hardware

    // --- INVENTORY FIELDS (MERGED FROM DEPRECATED INVENTORY ENTITY) ---
    @Column(name = "stock_quantity")
    private int stockQuantity = 0;

    @Column(name = "min_stock_level")
    private int minStockLevel = 10;


    // --- RELATIONSHIPS ---

    // 1. PARENT PRODUCT (Many Variants belong to One Product)
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore // Stops infinite recursion: Product -> Variant -> Product
    private Product product;
}