package com.Surakuri.Repository;

import com.Surakuri.Model.entity.User_Cart.CartItem;  // Correct package
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Common derived query
    List<CartItem> findByCartId(Long cartId);

    // Placeholder for advanced query
    // @Query("SELECT c FROM CartItem c WHERE c.quantity > :minQty")
    // List<CartItem> findCartItemsWithMinQuantity(@Param("minQty") int minQty);
}
