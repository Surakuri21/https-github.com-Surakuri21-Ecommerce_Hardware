package com.Surakuri.Model.entity.Other_Business_Entities;

import com.Surakuri.Model.entity.Products_Categories.Product;
import com.Surakuri.Model.entity.User_Cart.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "wishlists") // Explicitly map to SQL table
public class Wishlist {

    @Id
    // FIX: Use IDENTITY for MySQL
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long id;

    // RELATIONSHIP: One User has One Wishlist
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnore
    private User user;

    // RELATIONSHIP: A Wishlist can contain Many Products
    // We use a "Join Table" to store the links between Wishlists and Products
    @ManyToMany
    @JoinTable(
            name = "wishlist_products",
            joinColumns = @JoinColumn(name = "wishlist_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();
}