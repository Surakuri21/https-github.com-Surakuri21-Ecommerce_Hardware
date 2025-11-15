package com.Surakuri.Model.entity.Products_Categories;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Deal {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer discount;

    @OneToOne
    private Home_Category category;














}
