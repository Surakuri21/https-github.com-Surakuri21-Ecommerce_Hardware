package com.Surakuri.Service;

import com.Surakuri.Domain.PaymentOrderStatus;
import com.Surakuri.Exception.ProductOutOfStockException;
import com.Surakuri.Exception.ResourceNotFoundException;
import com.Surakuri.Model.dto.CheckoutRequest;
import com.Surakuri.Model.dto.OrderResponse; // <--- IMPORT THE DTO
import com.Surakuri.Model.entity.Other_Business_Entities.Address;
import com.Surakuri.Model.entity.Payment_Orders.Order;
import com.Surakuri.Model.entity.Payment_Orders.OrderItem;
import com.Surakuri.Model.entity.Products_Categories.ProductVariant;
import com.Surakuri.Model.entity.User_Cart.Cart;
import com.Surakuri.Model.entity.User_Cart.CartItem;
import com.Surakuri.Model.entity.User_Cart.User;
import com.Surakuri.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Transactional
    public OrderResponse checkout(Long userId, CheckoutRequest req) { // <--- Return DTO

        // 1. DATA FETCHING
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        Address address = addressRepository.findById(req.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty! Cannot checkout.");
        }

        // 2. CREATE ORDER SKELETON
        Order order = new Order();
        order.setUser(user);
        order.setOrderReferenceNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setStatus(PaymentOrderStatus.PENDING);
        // Snapshot the address (Fixes NullPointer if address fields are missing)
        String fullAddress = (address.getStreet() != null ? address.getStreet() : "") + ", " +
                (address.getCity() != null ? address.getCity() : "");
        order.setShippingAddressSnapshot(fullAddress);
        order.setShippingFee(new BigDecimal("150.00"));
        order.setCreatedAt(LocalDateTime.now());

        // 3. MOVE ITEMS & DEDUCT INVENTORY
        BigDecimal orderTotal = BigDecimal.ZERO;
        Set<OrderItem> orderItems = new HashSet<>();

        for (CartItem cartItem : cart.getCartItems()) {
            ProductVariant variant = cartItem.getVariant();
            // Check Stock
            if (variant.getStockQuantity() < cartItem.getQuantity()) {
                throw new ProductOutOfStockException(variant.getVariantName() + " is out of stock.");
            }

            // Deduct Inventory
            variant.setStockQuantity(variant.getStockQuantity() - cartItem.getQuantity());
            productVariantRepository.save(variant);

            // Create Order Item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setVariant(variant);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setMrpPrice(variant.getProduct().getMrp());
            orderItem.setSellingPrice(variant.getPrice());
            BigDecimal subtotal = variant.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
            orderItem.setSubtotal(subtotal);

            orderItems.add(orderItem);
            orderTotal = orderTotal.add(subtotal);
        }

        // 4. FINALIZE & SAVE
        order.setOrderItems(orderItems);
        order.setTotalAmount(orderTotal.add(order.getShippingFee()));

        Order savedOrder = orderRepository.save(order);

        // 5. CLEAR CART (Safe Method)
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cartRepository.save(cart);

        // 6. RETURN DTO (Prevents StackOverflow Error)
        return mapToResponse(savedOrder, req.getPaymentMethod().toString());
    }

    // --- HELPER: Convert Entity to DTO ---
    private OrderResponse mapToResponse(Order order, String paymentMethod) {
        OrderResponse res = new OrderResponse();
        res.setOrderId(order.getId());
        res.setOrderReference(order.getOrderReferenceNumber());
        res.setStatus(order.getStatus().toString());
        res.setTotalAmount(order.getTotalAmount());
        res.setCustomerName(order.getUser().getFirstName() + " " + order.getUser().getLastName());
        res.setShippingAddress(order.getShippingAddressSnapshot());
        res.setPaymentMethod(paymentMethod);
        res.setOrderDate(order.getCreatedAt());
        return res;
    }
}