package com.Surakuri.Model.entity.Products_Categories;

import com.Surakuri.Model.entity.Payment_Orders.OrderItem;
import com.Surakuri.Model.entity.User_Cart.CartItem;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal; // IMPORT THIS for money
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "products")
public class Product {

    @Id
    // CHANGE: Use IDENTITY for MySQL. 'AUTO' can sometimes cause errors in existing DBs.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id") // Maps to our SQL 'product_id'
    private Long id;

    // --- YOUR EXISTING FIELDS (Modified for Database Types) ---

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT") // Allows long descriptions
    private String description;

    private String imageUrl;

    // CRITICAL CHANGE: Use BigDecimal for money, not Integer.
    // MySQL 'DECIMAL' maps to Java 'BigDecimal'.
    // Integers cannot handle cents (e.g., 150.50 PHP) and have rounding issues.
    @Column(nullable = false)
    private BigDecimal price;

    private BigDecimal mrp; // Manufacturer's Recommended Price

    // --- NEW FIELDS (Integrated from my previous "Hardware" Entity) ---

    @Column(name = "sku", unique = true)
    private String sku; // Stock Keeping Unit (e.g., "CEM-40KG") - Vital for inventory

    @Column(name = "brand")
    private String brand; // Hardware specific (e.g., "Phelps Dodge", "Eagle Cement")

    @Column(name = "specifications", columnDefinition = "TEXT")
    private String specifications; // e.g., "Voltage: 220V, Thickness: 2mm"

    @Column(name = "weight_kg")
    private BigDecimal weightKg; // Vital for calculating shipping of heavy hardware

    @Column(name = "is_active")
    private boolean isActive = true; // Soft delete (hide instead of delete)

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now(); // Audit trail

    // --- YOUR RELATIONSHIPS (Kept exactly as you had them) ---

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;



    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();
}