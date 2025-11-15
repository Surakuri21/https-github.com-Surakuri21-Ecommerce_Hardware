package com.Surakuri.Model.entity.Payment_Orders;

import com.Surakuri.Domain.PaymentOrderStatus;
import com.Surakuri.Model.dto.PaymentDetails;
import com.Surakuri.Model.entity.User_Cart.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // total order amount stored directly
    private Long amount;

    @Enumerated(EnumType.STRING)
    private PaymentOrderStatus status = PaymentOrderStatus.PENDING;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "paymentMethod", column = @Column(name = "payment_method")),
            @AttributeOverride(name = "amount", column = @Column(name = "payment_amount")),
            @AttributeOverride(name = "transactionId", column = @Column(name = "transaction_id")),
            @AttributeOverride(name = "paymentStatus", column = @Column(name = "payment_status")),
            @AttributeOverride(name = "createdAt", column = @Column(name = "payment_created_at")),
            @AttributeOverride(name = "updatedAt", column = @Column(name = "payment_updated_at"))
    })
    private PaymentDetails paymentDetails;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    private Set<Order> orders = new HashSet<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
