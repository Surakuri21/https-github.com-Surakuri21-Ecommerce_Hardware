package com.Surakuri.Domain;

public enum PaymentOrderStatus {
    PENDING,  // The user clicked "Pay" but hasn't finished the process
    SUCCESS,  // The gateway confirmed the money is captured
    FAILED    // The card was declined or the user cancelled
}