package com.Surakuri.Model.entity.Products_Categories;

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
@Table(name = "categories") // Explicitly map to SQL table 'categories'
public class Category {

    @Id
    // FIX: Use IDENTITY to match MySQL AUTO_INCREMENT
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id") // Map to SQL Primary Key
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    // --- HIERARCHY (Self-Referencing) ---

    // Parent Category (e.g., "Plumbing" is the parent of "Pipes")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id") // Map to SQL Foreign Key 'parent_id'
    private Category parentCategory;

    // Sub-Categories (e.g., "Pipes", "Faucets" are children of "Plumbing")
    // This is optional, but very helpful for the Frontend Menu
    @OneToMany(mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private Set<Category> subCategories = new HashSet<>();
}