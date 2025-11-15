package com.Surakuri.Model.entity.Other_Business_Entities;

import com.Surakuri.Model.entity.User_Cart.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Verification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String otp;

    private String email;

    @ManyToOne
    private User user;


    @OneToOne
    private Seller seller;











}
