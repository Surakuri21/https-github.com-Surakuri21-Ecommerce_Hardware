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
// FIX: Exclude relationships
@EqualsAndHashCode(exclude = {"pickupAddress"})
@Table(name = "sellers")
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private Long id;

    @Column(name = "seller_name", nullable = false)
    private String sellerName;

    @Column(name = "mobile")
    private String mobile;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Embedded
    private BusinessDetails businessDetails = new BusinessDetails();

    @Embedded
    private BankDetails bankDetails = new BankDetails();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pickup_address_id")
    private Address pickupAddress;

    @Column(name = "tin_number")
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