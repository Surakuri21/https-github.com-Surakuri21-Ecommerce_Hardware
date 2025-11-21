package com.Surakuri.Model.entity.Products_Categories;

import com.Surakuri.Domain.HomeCategorySection;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "home_categories") // Matches your DB table
public class HomeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Matches AUTO_INCREMENT
    private Long id;

    private String name;

    private String image;

    // --- FIX START ---
    // Old Code: private String categoryId;
    // New Code: Link to the actual Category Entity
    @OneToOne
    @JoinColumn(name = "category_id") // Maps to the BIGINT column in MySQL
    private Category category;
    // --- FIX END ---

    @Enumerated(EnumType.STRING)
    private HomeCategorySection section;
}