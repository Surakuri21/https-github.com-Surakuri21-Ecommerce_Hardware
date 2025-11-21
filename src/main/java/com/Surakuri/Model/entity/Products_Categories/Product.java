package com.Surakuri.Model.entity.Products_Categories;

import com.Surakuri.Model.entity.Other_Business_Entities.Seller; // Import Seller
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String brand; // e.g. "Phelps Dodge"

    private String imageUrl;

    // Base Price (Optional, for display "Starts at...")
    private BigDecimal price = BigDecimal.ZERO;
    private BigDecimal mrp = BigDecimal.ZERO;

    @Column(name = "weight_kg")
    private BigDecimal weightKg = BigDecimal.ZERO;

    @Column(name = "sku")
    private String sku; // General SKU if needed

    @Column(columnDefinition = "TEXT")
    private String specifications;

    private boolean isActive = true;

    private LocalDateTime createdAt;

    // --- RELATIONSHIPS ---

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // FIX 1: Add the SELLER relationship (This fixes setSeller error)
    @ManyToOne
    @JoinColumn(name = "seller_id")
    @JsonIgnore // Prevent infinite loop when fetching Seller -> Products -> Seller
    private Seller seller;

    // FIX 2: Add the VARIANTS list with Cascade (This fixes setVariants error)
    // 'cascade = CascadeType.ALL' means "When I save the Product, save all these Variants automatically"
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> variants = new ArrayList<>();


    }
    