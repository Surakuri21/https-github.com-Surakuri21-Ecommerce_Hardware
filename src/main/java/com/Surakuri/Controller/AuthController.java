package com.Surakuri.Controller;

import com.Surakuri.Response.AuthResponse;
import com.Surakuri.Model.dto.LoginRequest;
import com.Surakuri.Model.dto.SignupRequest;
import com.Surakuri.Model.entity.User_Cart.User;
import com.Surakuri.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // ==========================================
    // 1. REGISTER ENDPOINT
    // URL: http://localhost:8080/auth/signup
    // ==========================================
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest req) throws Exception {

        // 1. Call Service to save user
        User savedUser = authService.registerUser(req);

        // 2. Create Success Response
        AuthResponse response = new AuthResponse();
        response.setJwt("token_placeholder"); // We will add real JWT later
        response.setMessage("Registration Successful! Welcome " + savedUser.getFirstName());
        response.setRole(savedUser.getRole());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ==========================================
    // 2. LOGIN ENDPOINT
    // URL: http://localhost:2121/auth/signin
    // ==========================================
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest req) {
        try {
            // 1. Call Service to check credentials
            User user = authService.loginUser(req); // You need to ensure loginUser is public in AuthService

            // 2. Create Success Response
            AuthResponse response = new AuthResponse();
            response.setJwt("token_placeholder");
            response.setMessage("Login Successful");
            response.setRole(user.getRole());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            // If password wrong or user not found
            AuthResponse response = new AuthResponse(null, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }
}