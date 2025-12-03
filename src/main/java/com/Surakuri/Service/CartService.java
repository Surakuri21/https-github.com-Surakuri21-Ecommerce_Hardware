package com.Surakuri.Service;

import com.Surakuri.Exception.ProductOutOfStockException;
import com.Surakuri.Exception.ResourceNotFoundException;
import com.Surakuri.Model.dto.AddItemRequest;
import com.Surakuri.Model.dto.CartItemResponse; // Import
import com.Surakuri.Model.dto.CartResponse;     // Import
import com.Surakuri.Model.entity.Products_Categories.ProductVariant;
import com.Surakuri.Model.entity.User_Cart.Cart;
import com.Surakuri.Model.entity.User_Cart.CartItem;
import com.Surakuri.Model.entity.User_Cart.User;
import com.Surakuri.Repository.CartItemRepository;
import com.Surakuri.Repository.CartRepository;
import com.Surakuri.Repository.ProductVariantRepository;
import com.Surakuri.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductVariantRepository variantRepository;

    @Transactional
    public CartResponse addItemToCart(Long userId, AddItemRequest req) {

        // 1. FIND & VALIDATE
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        ProductVariant variant = variantRepository.findById(req.getVariantId())
                .orElseThrow(() -> new ResourceNotFoundException("Product Variant not found"));

        // 2. CHECK STOCK
        if (variant.getStockQuantity() < req.getQuantity()) {
            throw new ProductOutOfStockException("Insufficient stock.");
        }

        // 3. ADD OR UPDATE ITEM
        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndVariantId(cart.getId(), variant.getId());

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + req.getQuantity());
            item.setSubtotal(variant.getPrice().multiply(new BigDecimal(item.getQuantity())));
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setVariant(variant);
            newItem.setQuantity(req.getQuantity());
            newItem.setSellingPrice(variant.getPrice());
            newItem.setSubtotal(variant.getPrice().multiply(new BigDecimal(req.getQuantity())));

            cartItemRepository.save(newItem);
            cart.getCartItems().add(newItem); // Update local list for calculation
        }

        // 4. RECALCULATE & SAVE
        Cart savedCart = calculateCartTotals(cart);

        // 5. RETURN DTO (Safe Response)
        return mapToResponse(savedCart);
    }

    public CartResponse findUserCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return mapToResponse(cart);
    }

    // --- HELPER: CALCULATE TOTALS ---
    private Cart calculateCartTotals(Cart cart) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalItems = 0;

        for (CartItem item : cart.getCartItems()) {
            totalAmount = totalAmount.add(item.getSubtotal());
            totalItems += item.getQuantity();
        }

        cart.setTotalSellingPrice(totalAmount);
        cart.setTotalItem(totalItems);
        return cartRepository.save(cart);
    }

    // --- HELPER: MAP TO DTO ---
    private CartResponse mapToResponse(Cart cart) {
        System.out.println("DEBUG: Starting MapToResponse..."); // LOG 1

        CartResponse res = new CartResponse();
        res.setCartId(cart.getId());
        res.setTotalSellingPrice(cart.getTotalSellingPrice());
        res.setTotalItems(cart.getTotalItem());

        System.out.println("DEBUG: Mapping User Name..."); // LOG 2
        if (cart.getUser() != null) {
            res.setCustomerName(cart.getUser().getFirstName() + " " + cart.getUser().getLastName());
        }

        List<CartItemResponse> itemDTOs = new ArrayList<>();

        if (cart.getCartItems() != null) {
            System.out.println("DEBUG: Found " + cart.getCartItems().size() + " items."); // LOG 3

            for (CartItem item : cart.getCartItems()) {
                System.out.println("DEBUG: Processing Item ID: " + item.getId()); // LOG 4

                CartItemResponse itemRes = new CartItemResponse();
                itemRes.setCartItemId(item.getId());
                itemRes.setPrice(item.getSellingPrice());
                itemRes.setQuantity(item.getQuantity());
                itemRes.setSubtotal(item.getSubtotal());

                if (item.getVariant() != null) {
                    System.out.println("DEBUG: Getting Variant Name..."); // LOG 5
                    itemRes.setVariantName(item.getVariant().getVariantName());

                    if (item.getVariant().getProduct() != null) {
                        System.out.println("DEBUG: Getting Product Name..."); // LOG 6
                        itemRes.setProductName(item.getVariant().getProduct().getName());
                        itemRes.setImageUrl(item.getVariant().getProduct().getImageUrl());
                    }
                }
                itemDTOs.add(itemRes);
            }
        }
        res.setItems(itemDTOs);

        System.out.println("DEBUG: Mapping Finished Successfully."); // LOG 7
        return res;
    }
}