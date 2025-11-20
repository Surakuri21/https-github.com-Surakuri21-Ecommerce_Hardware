package com.Surakuri.Model.entity.Payment_Orders;

import com.Surakuri.Domain.PaymentOrderStatus;
import com.Surakuri.Model.entity.User_Cart.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal; // IMPORT THIS for accurate money math
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "orders") // Matches SQL table 'orders'
public class Order {

    @Id
    // FIX: Use IDENTITY for MySQL. AUTO can cause issues with existing tables.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id") // Maps to SQL Primary Key
    private Long id;

    // --- NEW FIELDS FOR HARDWARE E-COMMERCE ---

    @Column(name = "order_reference_number", unique = true, nullable = false)
    private String orderReferenceNumber; // e.g., "ORD-2025-8821"

    @Column(name = "shipping_fee")
    private BigDecimal shippingFee; // Separate heavy item shipping costs

    @Column(name = "shipping_address_snapshot", columnDefinition = "TEXT")
    private String shippingAddressSnapshot; // Saves address as it was when ordered

    // --- EXISTING FIELDS (Optimized) ---

    // FIX: Changed Double to BigDecimal
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentOrderStatus status = PaymentOrderStatus.PENDING;

    @Column(name = "order_date") // Replaces createdAt to match DB standard
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // --- RELATIONSHIPS ---

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Maps to SQL Foreign Key
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "payment_order_id")
    private PaymentOrder paymentOrder;

    // --- LIFECYCLE HOOKS ---
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}