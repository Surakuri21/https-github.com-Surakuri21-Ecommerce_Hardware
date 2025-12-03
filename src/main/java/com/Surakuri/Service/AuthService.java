package com.Surakuri.Service;

import com.Surakuri.Domain.User_Role;
import com.Surakuri.Exception.UserAlreadyExistsException;
import com.Surakuri.Model.dto.LoginRequest;
import com.Surakuri.Model.dto.SignupRequest;
import com.Surakuri.Model.entity.User_Cart.Cart;
import com.Surakuri.Model.entity.User_Cart.User;
import com.Surakuri.Repository.CartRepository;
import com.Surakuri.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ==========================================
    // 1. REGISTER USER (Sign Up)
    // ==========================================
    public User registerUser(SignupRequest req) {

        // A. VALIDATION
        // Check if email already exists in MySQL
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new UserAlreadyExistsException("Email is already registered: " + req.getEmail());
        }

        // B. CREATE USER ENTITY
        User user = new User();
        user.setEmail(req.getEmail());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setUsername(req.getEmail()); // Default username is email

        // C. PH CONTEXT: FIX MOBILE NUMBER
        // Logic: "09171234567" -> "+639171234567"
        user.setMobile(normalizePhMobile(req.getMobile()));

        // D. SECURITY: HASH PASSWORD
        // Turns "password123" into "$2a$10$..."
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        // E. SET ROLE
        // If the request didn't specify a role, default to CUSTOMER
        if (req.getRole() == null) {
            user.setRole(User_Role.ROLE_CUSTOMER);
        } else {
            user.setRole(req.getRole());
        }

        user.setActive(true); // Auto-activate (Set to false if you implement Email Verification later)
        user.setCreatedAt(LocalDateTime.now());

        // F. SAVE USER TO DATABASE
        User savedUser = userRepository.save(user);

        // G. CREATE SHOPPING CART (CRITICAL STEP)
        // Every user must have a cart immediately upon sign-up.
        // If we skip this, the "Add to Cart" button will crash later.
        Cart cart = new Cart();
        cart.setUser(savedUser);
        cartRepository.save(cart);

        return savedUser;
    }

    // ==========================================
    // 2. LOGIN USER (Sign In)
    // ==========================================
    public User loginUser(LoginRequest req) throws Exception {

        // A. FIND USER
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new Exception("User not found with this email."));

        // B. CHECK PASSWORD
        // We use 'matches()' to compare the Raw Password (req) vs Hashed Password (DB)
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new Exception("Invalid Password");
        }

        return user;
    }

    // ==========================================
    // 3. HELPER: PHILIPPINE MOBILE FORMATTER
    // ==========================================
    private String normalizePhMobile(String mobile) {
        if (mobile == null || mobile.isEmpty()) return null;

        // Remove spaces, dashes, or parentheses
        // e.g., "(0917) 123-4567" -> "09171234567"
        String clean = mobile.replaceAll("[^0-9]", "");

        // If it starts with "09" (Standard PH Mobile), replace with "+63"
        if (clean.startsWith("09")) {
            return "+63" + clean.substring(1);
        }

        return clean;
    }
}