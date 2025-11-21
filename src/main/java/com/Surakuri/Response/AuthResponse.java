package com.Surakuri.Response;

import com.Surakuri.Domain.User_Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String jwt; // We will implement the actual token later
    private String message;
    private User_Role role;
}