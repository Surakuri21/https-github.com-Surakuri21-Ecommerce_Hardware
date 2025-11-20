package com.Surakuri.Model.entity.Other_Business_Entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal; // Standard for Money
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seller_reports") // Explicitly map to SQL table
public class SellerReport {

    @Id
    // FIX: Use IDENTITY for MySQL
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // RELATIONSHIP: One Seller has One Summary Report
    @OneToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "seller_id")
    private Seller seller;

    // --- FINANCIAL STATS (Use BigDecimal) ---

    @Column(name = "total_earnings")
    private BigDecimal totalEarnings = BigDecimal.ZERO; // What they take home

    @Column(name = "total_sales")
    private BigDecimal totalSales = BigDecimal.ZERO;    // Gross amount sold

    @Column(name = "total_refunds")
    private BigDecimal totalRefunds = BigDecimal.ZERO;

    @Column(name = "total_tax")
    private BigDecimal totalTax = BigDecimal.ZERO;      // Withholding Tax / VAT

    @Column(name = "net_earnings")
    private BigDecimal netEarnings = BigDecimal.ZERO;   // Sales - Refunds - Tax

    // --- ORDER COUNTS ---

    @Column(name = "total_orders")
    private Integer totalOrders = 0;

    @Column(name = "canceled_orders") // Fixed typo: 'cancelOrders' -> 'canceled_orders'
    private Integer canceledOrders = 0;

    @Column(name = "total_transactions")
    private Integer totalTransactions = 0;

    // --- METADATA ---

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated = LocalDateTime.now();
}