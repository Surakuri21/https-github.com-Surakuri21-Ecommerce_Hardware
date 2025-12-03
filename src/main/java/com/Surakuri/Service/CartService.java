package com.Surakuri.Service;

import com.Surakuri.Exception.ProductOutOfStockException;
import com.Surakuri.Exception.ResourceNotFoundException;
import com.Surakuri.Model.dto.AddItemRequest;
import com.Surakuri.Model.dto.CartItemResponse;
import com.Surakuri.Model.dto.CartResponse;
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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        ProductVariant variant = variantRepository.findById(req.getVariantId())
                .orElseThrow(() -> new ResourceNotFoundException("Product Variant not found"));

        if (variant.getStockQuantity() < req.getQuantity()) {
            throw new ProductOutOfStockException("Insufficient stock for " + variant.getVariantName());
        }

        Optional<CartItem> existingItemOpt = cart.getCartItems().stream()
                .filter(item -> item.getVariant().getId().equals(req.getVariantId()))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            CartItem item = existingItemOpt.get();
            item.setQuantity(item.getQuantity() + req.getQuantity());
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setVariant(variant);
            newItem.setQuantity(req.getQuantity());
            cart.getCartItems().add(cartItemRepository.save(newItem));
        }

        // We no longer save totals to the DB, just the items.
        Cart savedCart = cartRepository.save(cart);

        // The mapToResponse method will now calculate all totals dynamically.
        return mapToResponse(savedCart);
    }

    public CartResponse findUserCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user ID: " + userId));
        return mapToResponse(cart);
    }

    private CartResponse mapToResponse(Cart cart) {
        CartResponse res = new CartResponse();
        res.setCartId(cart.getId());

        if (cart.getUser() != null) {
            res.setCustomerName(cart.getUser().getFirstName() + " " + cart.getUser().getLastName());
        }

        List<CartItemResponse> itemDTOs = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        int totalItems = 0;

        if (cart.getCartItems() != null) {
            for (CartItem item : cart.getCartItems()) {
                ProductVariant variant = item.getVariant();
                if (variant == null) continue; // Skip if variant is missing

                BigDecimal subtotal = variant.getPrice().multiply(new BigDecimal(item.getQuantity()));

                CartItemResponse itemRes = new CartItemResponse();
                itemRes.setCartItemId(item.getId());
                itemRes.setPrice(variant.getPrice());
                itemRes.setQuantity(item.getQuantity());
                itemRes.setSubtotal(subtotal);
                itemRes.setVariantName(variant.getVariantName());

                if (variant.getProduct() != null) {
                    itemRes.setProductName(variant.getProduct().getName());
                    itemRes.setImageUrl(variant.getProduct().getImageUrl());
                }

                itemDTOs.add(itemRes);

                // Aggregate totals
                totalAmount = totalAmount.add(subtotal);
                totalItems += item.getQuantity();
            }
        }

        res.setItems(itemDTOs);
        res.setTotalSellingPrice(totalAmount);
        res.setTotalItems(totalItems);

        return res;
    }
}