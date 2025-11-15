package com.Surakuri.Repository;

import com.Surakuri.Model.entity.User_Cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // Find cart by user ID
    Optional<Cart> findByUserId(Long userId);
}
