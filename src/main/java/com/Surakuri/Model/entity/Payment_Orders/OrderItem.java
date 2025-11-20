package com.Surakuri.Model.entity.Payment_Orders;

import com.Surakuri.Model.entity.Products_Categories.Product;
import com.Surakuri.Model.entity.Products_Categories.ProductVariant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal; // Standard for money

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "order_items") // Explicitly map to SQL table
public class OrderItem {

    @Id
    // FIX: Use IDENTITY for MySQL
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- RELATIONSHIPS ---

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // <--- The name MUST be 'product'

    // FIX: Link to the specific Variant (SKU/Size), not generic Product
    @ManyToOne
    @JoinColumn(name = "variant_id", nullable = false)
    private ProductVariant variant;

    // --- ORDER DETAILS ---

    private int quantity;

    // FIX: Use BigDecimal for money
    @Column(name = "mrp_price")
    private BigDecimal mrpPrice;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice; // The price AT THE MOMENT of purchase

    @Column(name = "subtotal") // quantity * sellingPrice
    private BigDecimal subtotal;

    // --- HELPER METHODS ---

    // This allows the Frontend to easily ask "What is the name of this item?"
    // without you having to store the string "Cement" in this table.
    public String getProductName() {
        return variant.getProduct().getName();
    }

    public String getBrand() {
        return variant.getProduct().getBrand();
    }

    public String getSizeOrSpec() {
        return variant.getVariantName(); // e.g. "40kg Bag"
    }
}