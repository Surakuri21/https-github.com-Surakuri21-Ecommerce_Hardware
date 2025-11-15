package com.Surakuri.Model.entity.Payment_Orders;

import com.Surakuri.Domain.PaymentOrderStatus;
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
@Table(name = "orders") // "order" is a reserved SQL keyword
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    private Double totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private PaymentOrderStatus status = PaymentOrderStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "payment_order_id")
    private PaymentOrder paymentOrder;
}
