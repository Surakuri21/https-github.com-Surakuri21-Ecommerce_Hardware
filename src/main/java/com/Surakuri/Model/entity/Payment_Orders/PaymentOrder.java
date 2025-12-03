package com.Surakuri.Model.entity.Payment_Orders;

import com.Surakuri.Domain.PaymentOrderStatus;
import com.Surakuri.Model.dto.PaymentDetails;
import com.Surakuri.Model.entity.User_Cart.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_orders")
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_order_id")
    private Long id;

    // 1. This maps to the SQL column 'amount'
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentOrderStatus status = PaymentOrderStatus.PENDING;

    // 2. This embedded object also has an 'amount' field.
    // We MUST rename it to 'payment_amount' to avoid the "Duplicate Column" error.
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "paymentMethod", column = @Column(name = "payment_method")),

            @AttributeOverride(name = "paymentId", column = @Column(name = "transaction_reference_id")),

            @AttributeOverride(name = "status", column = @Column(name = "payment_status")),
            @AttributeOverride(name = "amount", column = @Column(name = "payment_amount"))
    })
    private PaymentDetails paymentDetails;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "paymentOrder", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Order> orders = new HashSet<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() { this.createdAt = LocalDateTime.now(); }

    @PreUpdate
    protected void onUpdate() { this.updatedAt = LocalDateTime.now(); }
}