package com.Surakuri.Model.entity.Payment_Orders;

import com.Surakuri.Domain.PaymentOrderStatus;
import com.Surakuri.Model.dto.PaymentDetails;
import com.Surakuri.Model.entity.User_Cart.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal; // Standard for money
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "payment_orders") // Explicitly map to SQL table
public class PaymentOrder {

    @Id
    // FIX: Use IDENTITY to match MySQL AUTO_INCREMENT
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_order_id")
    private Long id;

    // FIX: Use BigDecimal for money
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentOrderStatus status = PaymentOrderStatus.PENDING;



    // --- EMBEDDED DETAILS ---
    // This flattens the PaymentDetails DTO columns into this table
    // Inside PaymentOrder.java
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "paymentMethod", column = @Column(name = "payment_method")),
            @AttributeOverride(name = "status", column = @Column(name = "payment_status")),
            @AttributeOverride(name = "amount", column = @Column(name = "payment_amount")),
            // MAP generic 'paymentId' to SQL column 'transaction_reference_id'
            @AttributeOverride(name = "paymentId", column = @Column(name = "transaction_reference_id"))
    })
    private PaymentDetails paymentDetails;



    // --- RELATIONSHIPS ---

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    // FIX: Added 'mappedBy'.
    // This refers to the 'private PaymentOrder paymentOrder;' field in your Order.java
    @OneToMany(mappedBy = "paymentOrder", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

    // --- TIMESTAMPS ---

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}