package com.Surakuri.Model.entity.Products_Categories;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "product_variants") // Maps to SQL table 'product_variants'
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "variant_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String sku; // Stock Keeping Unit (e.g., "CEM-40KG")

    @Column(name = "variant_name")
    private String variantName; // e.g., "40kg Bag" or "1/2 inch"

    // PRICING
    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "weight_kg")
    private BigDecimal weightKg; // Vital for shipping calc

    // RELATIONSHIP: Link back to the main Product
    // This connects the Variant (Size) to the Parent Product (Name)
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}