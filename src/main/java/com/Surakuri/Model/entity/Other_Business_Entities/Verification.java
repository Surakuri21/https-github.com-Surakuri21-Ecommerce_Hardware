package com.Surakuri.Model.entity.Other_Business_Entities;

import com.Surakuri.Model.entity.User_Cart.User;
// import com.Surakuri.Model.entity.Seller_Side.Seller; // Make sure to import your Seller entity!
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "verification_tokens") // Maps to SQL table
public class Verification {

    @Id
    // FIX: Use IDENTITY for MySQL
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String otp; // The 6-digit code (e.g., "882190")

    private String email; // Good backup in case User object isn't fully created yet

    // --- SECURITY FIELD (Crucial) ---
    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    // --- RELATIONSHIPS ---

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id") // Links to Users table
    private User user;

    // You added this (Multi-Vendor support), so we map it here.
    // Note: Ensure your 'Seller' entity exists!
    @OneToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    // --- HELPER METHODS ---

    // 1. Check if token is expired
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryDate);
    }

    // 2. Constructor helper to set OTP and Auto-Expire in 15 minutes
    public Verification(String otp, User user) {
        this.otp = otp;
        this.user = user;
        this.expiryDate = LocalDateTime.now().plusMinutes(15); // Standard OTP life
    }
}