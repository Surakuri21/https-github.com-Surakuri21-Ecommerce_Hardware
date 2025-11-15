package com.Surakuri.Repository;

import com.Surakuri.Model.entity.Other_Business_Entities.Address;  // Adjust if package is different
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    // Add custom queries if needed


}
