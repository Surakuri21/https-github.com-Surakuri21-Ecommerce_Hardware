package com.Surakuri.Model.entity.Products_Categories;

import com.Surakuri.Model.entity.Other_Business_Entities.Seller;
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
// ⚠️ CRITICAL FIX: Exclude ALL relationships
@EqualsAndHashCode(exclude = {"variants", "seller", "category"})
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

    private String brand;
    private String imageUrl;
    private BigDecimal price = BigDecimal.ZERO;
    private BigDecimal mrp = BigDecimal.ZERO;

    @Column(name = "weight_kg")
    private BigDecimal weightKg = BigDecimal.ZERO;

    private String sku;

    @Column(columnDefinition = "TEXT")
    private String specifications;

    private boolean isActive = true;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    @JsonIgnore
    private Seller seller;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ProductVariant> variants = new ArrayList<>();

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }
}