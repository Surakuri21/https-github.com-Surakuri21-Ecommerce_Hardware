package com.Surakuri.Repository;

import com.Surakuri.Model.entity.Products_Categories.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
