package com.Surakuri.Model.dto;

import com.Surakuri.Domain.PaymentMethod;
import com.Surakuri.Domain.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Embeddable
public class PaymentDetails {

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    private String transactionId;

    private Double amount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
