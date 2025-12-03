package com.Surakuri.Domain;

public enum OrderStatus {


    PENDING,      // Order placed, waiting for confirmation
    CONFIRMED,    // Seller/Admin accepted the order
    PACKED,       // Items are in the box
    SHIPPED,      // Handed to courier (LBC/J&T)
    DELIVERED,    // Customer received items
    CANCELLED     // Order stopped
}