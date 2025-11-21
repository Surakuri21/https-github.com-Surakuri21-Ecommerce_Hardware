package com.Surakuri.Model.entity.User_Cart;

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
@Table(name = "cart_items") // Explicitly map to SQL table
public class CartItem {

    @Id
    // FIX: Use IDENTITY to match MySQL AUTO_INCREMENT
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne
    @JsonIgnore // Prevents infinite loops when converting to JSON
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    // --- CHANGED: Link to Variant (Size/SKU) instead of generic Product ---
    // This matches the SQL Foreign Key 'variant_id'
    @ManyToOne
    @JoinColumn(name = "variant_id", nullable = false)
    private ProductVariant variant;

    // You don't need 'private String size' anymore,
    // because the ProductVariant object ALREADY contains the size/weight info!

    private int quantity = 1;

    // --- PRICING (Use BigDecimal) ---

    @Column(name = "mrp_price")
    private BigDecimal mrpPrice;

    @Column(name = "selling_price") // This is the price snapshot at moment of add-to-cart
    private BigDecimal sellingPrice;

    @Column(name = "subtotal") // Calculated as: quantity * sellingPrice
    private BigDecimal subtotal;

    // --- Helper Method to get Product details easily ---
    // This lets you allow frontend to say cartItem.getProductName()
    public String getProductName() {
        return variant.getProduct().getName();
    }

    public String getSizeOrSpec() {
        return variant.getVariantName(); // e.g., "40kg Bag"
    }
}