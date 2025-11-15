package com.Surakuri.Model.entity.Other_Business_Entities;


import com.Surakuri.Model.entity.Payment_Orders.Order;
import com.Surakuri.Model.entity.User_Cart.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User customer;

    @OneToOne
    private Order order;

    @ManyToOne
    private Seller seller;

    private LocalDateTime date = LocalDateTime.now();











}
