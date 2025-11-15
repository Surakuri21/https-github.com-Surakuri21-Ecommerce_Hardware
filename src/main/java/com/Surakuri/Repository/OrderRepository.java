package com.Surakuri.Repository;

import com.Surakuri.Model.entity.Payment_Orders.Order;
import com.Surakuri.Domain.PaymentOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Orders by user
    List<Order> findByUserId(Long userId);

    // Orders by PaymentOrder status
    List<Order> findByPaymentOrderStatus(PaymentOrderStatus status);
}
