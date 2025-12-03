package com.Surakuri.Controller;

import com.Surakuri.Model.dto.AddItemRequest;
import com.Surakuri.Model.dto.CartResponse; // <--- MUST IMPORT THIS
import com.Surakuri.Model.entity.User_Cart.User;
import com.Surakuri.Repository.UserRepository;
import com.Surakuri.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    // ==========================================
    // 1. ADD TO CART
    // ==========================================
    @PutMapping("/add")
    // ERROR CHECK: Does this say 'ResponseEntity<Cart>'?
    // CHANGE IT TO: 'ResponseEntity<CartResponse>'
    public ResponseEntity<CartResponse> addItemToCart(@RequestBody AddItemRequest req) {

        User user = userRepository.findByEmail("builder@hardware.ph").orElse(null);
        Long userId = (user != null) ? user.getId() : 1L;

        // ERROR CHECK: Ensure service returns DTO
        CartResponse updatedCart = cartService.addItemToCart(userId, req);

        return ResponseEntity.ok(updatedCart);
    }

    // ==========================================
    // 2. VIEW CART
    // ==========================================
    @GetMapping
    // ERROR CHECK: Change 'Cart' to 'CartResponse' here too!
    public ResponseEntity<CartResponse> findUserCart() {

        User user = userRepository.findByEmail("builder@hardware.ph").orElse(null);
        Long userId = (user != null) ? user.getId() : 1L;

        CartResponse cart = cartService.findUserCart(userId);

        return ResponseEntity.ok(cart);
    }
}