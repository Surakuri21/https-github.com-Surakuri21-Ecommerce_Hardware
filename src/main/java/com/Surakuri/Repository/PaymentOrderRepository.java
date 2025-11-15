package com.Surakuri.Repository;

import com.Surakuri.Model.entity.Payment_Orders.PaymentOrder;
import com.Surakuri.Domain.PaymentOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {

    // Find all orders by status
    List<PaymentOrder> findByStatus(PaymentOrderStatus status);

    // Find by transaction ID inside embedded PaymentDetails
    Optional<PaymentOrder> findByPaymentDetailsTransactionId(String transactionId);

    // Find orders by user ID
    List<PaymentOrder> findByUserId(Long userId);
}
