package com.Surakuri.Controller;

import com.Surakuri.Model.dto.CheckoutRequest;
import com.Surakuri.Model.dto.OrderResponse;
import com.Surakuri.Model.entity.User_Cart.User;
import com.Surakuri.Service.OrderService;
import com.Surakuri.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling all order-related operations.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    /**
     * Creates an order from the user's current shopping cart.
     * This is a protected endpoint, and the user is identified via their JWT token.
     *
     * @param req The request body containing checkout details like addressId and paymentMethod.
     * @return A response DTO with the details of the newly created order.
     */
    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> checkout(@RequestBody CheckoutRequest req) {
        // 1. Get the authenticated user securely from the JWT.
        User user = userService.findUserProfileByJwt();

        // 2. Call the existing service method to perform the checkout logic.
        OrderResponse orderResponse = orderService.checkout(user.getId(), req);

        // 3. Return the successful order response.
        return ResponseEntity.ok(orderResponse);
    }
}