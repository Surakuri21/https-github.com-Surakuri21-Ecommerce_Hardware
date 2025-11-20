package com.Surakuri.Model.entity.Products_Categories;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code; // e.g., "SUMMER2025"

    @Column(name = "discount_percentage")
    private double discountPercentage; // e.g., 10.0 for 10% off

    @Column(name = "validity_start_date")
    private LocalDate validityStartDate;

    @Column(name = "validity_end_date")
    private LocalDate validityEndDate;

    @Column(name = "min_order_amount")
    private BigDecimal minOrderAmount; // e.g., "Use this only for orders above ₱1,000"

    private boolean isActive = true;
}