/*

package com.Surakuri.Controller;


import com.Surakuri.Domain.User_Role;
import com.Surakuri.Model.entity.User_Cart.User;
import com.Surakuri.Repository.UserRepository;
import com.Surakuri.Response.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;


    @PostMapping("/signup")   // <--- Defines the final path: /signup
    public ResponseEntity<User> createUserHandler(@RequestBody SignupRequest req){
    // Logic to handle user creation, validation, and saving to the database


        User user =  new User();
        user.setEmail(req.getEmail());
        user.setFullName(req.getFullName());

        User savedUser = userRepository.save(user);

           return ResponseEntity.ok(savedUser);
     }

}
*/