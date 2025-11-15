package com.Surakuri.Model.entity.Products_Categories;

import com.Surakuri.Model.entity.User_Cart.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;
    private int discount;

    @ManyToMany(mappedBy = "usedCoupons")
    private Set<User> usedByUsers = new HashSet<>();
}
