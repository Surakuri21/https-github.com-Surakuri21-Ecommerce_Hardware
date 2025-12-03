package com.Surakuri.Controller;

import com.Surakuri.Model.dto.CheckoutRequest;
import com.Surakuri.Model.dto.OrderResponse; // <--- IMPORT DTO
import com.Surakuri.Model.entity.User_Cart.User;
import com.Surakuri.Repository.UserRepository;
import com.Surakuri.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(@RequestBody CheckoutRequest req) { // <--- Return DTO

        // Hardcoded User 1 (Bob) for testing until JWT is ready
        User user = userRepository.findByEmail("builder@hardware.ph").orElse(null);
        Long userId = (user != null) ? user.getId() : 1L;

        // FIX: Variable type must be OrderResponse, NOT Order
        OrderResponse response = orderService.checkout(userId, req);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}