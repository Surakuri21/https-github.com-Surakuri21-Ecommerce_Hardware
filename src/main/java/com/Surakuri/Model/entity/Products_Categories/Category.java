package com.Surakuri.Model.entity.Products_Categories;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;  // addition

    @NotNull
    @Column(unique = true)
    private String categoryId;

    @ManyToOne
    private Category parentCategory;

     @NotNull
    private Integer level;

}

