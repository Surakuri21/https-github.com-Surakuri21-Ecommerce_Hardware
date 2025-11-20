package com.Surakuri.Controller;

import com.Surakuri.Model.entity.User_Cart.User;
import com.Surakuri.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private UserRepository userRepository;

    // URL: http://localhost:8080/api/test/users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        // This fetches actual data from your MySQL Workbench
        return userRepository.findAll();
    }

    // URL: http://localhost:8080/api/test/hello
    @GetMapping("/hello")
    public String sayHello() {
        return "Philippines Hardware Store API is Running! 🇵🇭";
    }
}