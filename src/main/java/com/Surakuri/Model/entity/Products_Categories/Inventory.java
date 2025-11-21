package com.Surakuri.Model.entity.Products_Categories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long id;

    // RELATIONSHIP: One Variant (SKU) has One Inventory record
    // This is the "Owning Side" because it holds the 'variant_id' foreign key
    @OneToOne
    @JoinColumn(name = "variant_id", nullable = false)
    @JsonIgnore // Prevent loops if you fetch Variant -> Inventory -> Variant
    private ProductVariant variant;

    @Column(name = "quantity_on_hand")
    private int quantityOnHand;

    @Column(name = "min_stock_level")
    private int minStockLevel = 10; // Default alert threshold

    // --- HELPER METHODS ---

    // Check if stock is sufficient for an order
    public boolean hasStock(int requestedQuantity) {
        return this.quantityOnHand >= requestedQuantity;
    }

    // Decrease stock (e.g. when order is placed)
    public void decreaseStock(int quantity) {
        if (hasStock(quantity)) {
            this.quantityOnHand -= quantity;
        } else {
            throw new RuntimeException("Insufficient stock for SKU: " + variant.getSku());
        }
    }

    // Increase stock (e.g. when supplier delivers or return happens)
    public void increaseStock(int quantity) {
        this.quantityOnHand += quantity;
    }
}