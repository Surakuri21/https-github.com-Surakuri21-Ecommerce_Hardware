package com.Surakuri.Model.entity.Other_Business_Entities;

import com.Surakuri.Model.entity.User_Cart.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "customer_addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    // --- CRITICAL FIX 1: RELATIONSHIP ---

    // Changed to ManyToOne: A User can have multiple addresses.
    // Changed to nullable=true: This address might belong to a Seller (who isn't a User).
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    @JsonIgnore // Prevents infinite loops in JSON
    private User user;

    // Note: We DO NOT add a 'private Seller seller' field here.
    // Why? Because the 'sellers' table holds the Foreign Key to this address.
    // This is a "Unidirectional" relationship from Seller -> Address.

    // --- CRITICAL FIX 2: GENERIC FIELDS ---

    @Column(name = "contact_person_name") // Unified name (Replaces recipientName)
    private String contactPersonName;

    @Column(name = "contact_mobile")      // Unified mobile (Replaces phoneNumber)
    private String contactMobile;

    // --- PHILIPPINE ADDRESS STANDARD ---

    private String addressLabel; // e.g., "Warehouse 1", "Home"

    private String region;       // NCR, Region VII

    private String province;     // Cebu

    private String city;         // Lapu-Lapu City

    private String barangay;     // Brgy. Basak

    private String street;       // Lot 4, Block 2

    private String postalCode;

    private String additionalNotes; // "Green gate"

    @Column(name = "is_default")
    private boolean isDefault = false;
}