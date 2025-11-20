package com.Surakuri.Model.entity.User_Cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal; // Vital for money
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "carts") // Explicitly map to SQL table 'carts'
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    // --- PRICING FIELDS (Use BigDecimal) ---

    @Column(name = "total_selling_price")
    private BigDecimal totalSellingPrice = BigDecimal.ZERO;

    @Column(name = "total_item")
    private int totalItem = 0;

    @Column(name = "total_mrp_price") // Manufacturer's Price (Before discount)
    private BigDecimal totalMrpPrice = BigDecimal.ZERO;

    @Column(name = "discount")
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(name = "coupon_code")
    private String couponCode;
}