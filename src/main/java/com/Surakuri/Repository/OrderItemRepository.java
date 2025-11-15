package com.Surakuri.Repository;

import com.Surakuri.Model.entity.Payment_Orders.OrderItem;  // Adjust package if different
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);
    List<OrderItem> findByProductId(Long productId);// Example custom query


}
