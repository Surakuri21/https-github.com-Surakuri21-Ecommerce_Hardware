package com.Surakuri.Model.entity.User_Cart;

import com.Surakuri.Domain.User_Role;
import com.Surakuri.Model.entity.Other_Business_Entities.Address;
import com.Surakuri.Model.entity.Products_Categories.Coupon;
import com.Surakuri.Model.entity.Payment_Orders.PaymentOrder;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime; // Added for audit trails
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "users") // Explicitly map to the 'users' table in MySQL
public class User {

    @Id
    // FIX: Use IDENTITY for MySQL AUTO_INCREMENT. 'AUTO' often creates a hibernate_sequence table you don't want.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // Maps to the SQL column 'user_id'
    private Long id;

    // --- NEW FIELDS FROM DATABASE ---

    @Column(nullable = false, unique = true)
    private String username; // Required for login

    @Column(name = "password_hash", nullable = false)
    private String password; // Stores the BCrypt hash

    // FIX: Database splits name for better sorting/filtering
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    // --- EXISTING FIELDS ---

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone") // Mapped to DB column 'phone' instead of 'mobile'
    private String mobile;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private User_Role role = User_Role.ROLE_CUSTOMER;

    // --- AUDIT FIELDS (From Database) ---

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // --- RELATIONSHIPS (Kept exactly as you had them) ---

    /// In User.java

// Changed from @OneToOne to @OneToMany
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PaymentOrder> paymentOrders = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_coupons",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id")
    )
    private Set<Coupon> usedCoupons = new HashSet<>();

    // --- HELPER METHOD ---
    // If your service layer calls user.getFullName(), this ensures it still works
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }
}