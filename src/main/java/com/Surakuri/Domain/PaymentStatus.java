package com.Surakuri.Domain;

public enum PaymentStatus {
    PENDING,    // User clicked "Pay" but hasn't finished GCash flow
    COMPLETED,  // Money received
    FAILED,     // Insufficient balance or error
    REFUNDED    // Order cancelled, money returned
}