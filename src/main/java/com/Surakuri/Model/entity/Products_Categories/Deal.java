package com.Surakuri.Model.entity.Products_Categories;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "deals") // Explicitly map to SQL table
public class Deal {

    @Id
    // FIX: Use IDENTITY to match MySQL AUTO_INCREMENT
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deal_id")
    private Long id;

    @Column(nullable = false)
    private Integer discount; // e.g., 20 (for 20% off)

    // RELATIONSHIP: Link this deal to a specific Homepage Section
    // Usage: If HomeCategory is "Summer Sale", this Deal attaches the "20% Off" tag to it.
    @OneToOne
    @JoinColumn(name = "home_category_id") // Maps to SQL Foreign Key
    private HomeCategory category;
}