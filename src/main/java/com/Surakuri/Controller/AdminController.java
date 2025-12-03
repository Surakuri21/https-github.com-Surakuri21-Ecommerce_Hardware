package com.Surakuri.Controller;

import com.Surakuri.Model.entity.Other_Business_Entities.Seller;
import com.Surakuri.Service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private SellerService sellerService;

    // PUT http://localhost:2121/api/admin/sellers/1/approve
    @PutMapping("/sellers/{sellerId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Seller> approveSeller(@PathVariable Long sellerId) {
        Seller updatedSeller = sellerService.approveSeller(sellerId);
        return ResponseEntity.ok(updatedSeller);
    }
}