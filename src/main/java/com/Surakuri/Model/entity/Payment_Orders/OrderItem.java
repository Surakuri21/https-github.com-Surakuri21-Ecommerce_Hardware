package com.Surakuri.Model.entity.Payment_Orders;

import com.Surakuri.Model.entity.Products_Categories.ProductVariant;
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
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // --- FIX START ---
    // OLD CODE: private Product product;
    // NEW CODE: Link to the specific Variant (SKU)
    @ManyToOne
    @JoinColumn(name = "variant_id", nullable = false)
    private ProductVariant variant;
    // --- FIX END ---

    // You might have 'private String size;' here too.
    // You can remove it because 'variant' already knows the size.

    private int quantity;

    @Column(name = "mrp_price")
    private BigDecimal mrpPrice;

    @Column(name = "selling_price")
    private BigDecimal sellingPrice;

    @Column(name = "subtotal")
    private BigDecimal subtotal;
}