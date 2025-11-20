package com.Surakuri.Model.entity.Other_Business_Entities;

import com.Surakuri.Domain.AccountStatus;
import com.Surakuri.Domain.User_Role;
import com.Surakuri.Model.dto.BankDetails;
import com.Surakuri.Model.dto.BusinessDetails;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "sellers") // Explicitly map to SQL table 'sellers'
public class Seller {

    @Id
    // FIX: Use IDENTITY for MySQL compatibility
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Long id;

    @Column(name = "seller_name", nullable = false)
    private String sellerName; // Display name (e.g., "Cebu Hardware Hub")

    @Column(name = "mobile")
    private String mobile;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // Ensure this stores the Hash, not plain text

    // --- EMBEDDED DETAILS ---
    // These fields will be "flattened" into the 'sellers' table

    @Embedded
    private BusinessDetails businessDetails = new BusinessDetails();

    @Embedded
    private BankDetails bankDetails = new BankDetails();

    // --- ADDRESS (Pickup Point) ---
    // This reuses your Address entity.
    // NOTE: You must update your Address entity to allow linking to a Seller.
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pickup_address_id")
    private Address pickupAddress;

    // --- PHILIPPINE TAX INFO ---
    // Changed 'GSTIN' (India) to 'TIN' (Philippines)
    @Column(name = "tin_number", unique = true)
    private String TIN;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private User_Role role = User_Role.ROLE_SELLER;

    @Column(name = "is_email_verified")
    private boolean isEmailVerified = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;
}