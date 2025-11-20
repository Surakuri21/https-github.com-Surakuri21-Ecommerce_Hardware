package com.Surakuri.Model.entity.Other_Business_Entities;

import com.Surakuri.Domain.PaymentStatus; // You'll need an Enum for this (SUCCESS, FAILED)
import com.Surakuri.Model.entity.Payment_Orders.Order;
import com.Surakuri.Model.entity.User_Cart.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal; // Always use BigDecimal for money
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "transactions") // This acts as your Financial Ledger
public class Transaction {

    @Id
    // FIX: Use IDENTITY for MySQL
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- RELATIONSHIPS ---

    @ManyToOne
    @JoinColumn(name = "customer_id") // Who paid?
    @JsonIgnore
    private User customer;

    @OneToOne
    @JoinColumn(name = "order_id") // For which order?
    private Order order;

    @ManyToOne
    @JoinColumn(name = "seller_id") // Who receives the money? (Vital for Multi-Vendor)
    private Seller seller;

    // --- FINANCIAL DETAILS (The missing pieces) ---

    @Column(name = "transaction_reference")
    private String transactionId; // e.g., "GCASH-9981-2231" or Stripe Payment ID

    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING; // PENDING, COMPLETED, FAILED, REFUNDED

    private LocalDateTime date = LocalDateTime.now();
}