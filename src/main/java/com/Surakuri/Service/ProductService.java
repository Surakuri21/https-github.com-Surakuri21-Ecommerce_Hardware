package com.Surakuri.Service;

import com.Surakuri.Model.dto.CreateProductRequest;
import com.Surakuri.Model.dto.VariantRequest;
import com.Surakuri.Model.entity.Other_Business_Entities.Seller;
import com.Surakuri.Model.entity.Products_Categories.Category;
import com.Surakuri.Model.entity.Products_Categories.Inventory;
import com.Surakuri.Model.entity.Products_Categories.Product;
import com.Surakuri.Model.entity.Products_Categories.ProductVariant;
import com.Surakuri.Repository.CategoryRepository;
import com.Surakuri.Repository.ProductRepository;
import com.Surakuri.Repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @Transactional
    public Product createProduct(CreateProductRequest req, Long sellerId) {

        // 1. FIND SELLER
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        // 2. FIND OR CREATE CATEGORY
        Category category = categoryRepository.findByName(req.getCategoryName());
        if (category == null) {
            category = new Category();
            category.setName(req.getCategoryName());
            categoryRepository.save(category);
        }

        // 3. CREATE PARENT PRODUCT
        Product product = new Product();
        product.setSeller(seller);
        product.setCategory(category);
        product.setName(req.getName());
        product.setBrand(req.getBrand());
        product.setDescription(req.getDescription());
        product.setImageUrl(req.getImageUrl());

        // Set Price/Weight from first variant if available
        if (!req.getVariants().isEmpty()) {
            product.setPrice(req.getVariants().get(0).getPrice());
            product.setWeightKg(req.getVariants().get(0).getWeight());
        }

        product.setCreatedAt(LocalDateTime.now());
        product.setActive(true);

        // SAVE PARENT FIRST (To get ID)
        Product savedProduct = productRepository.save(product);

        // 4. PROCESS VARIANTS
        // FIX: Don't create a new list. Use the list that is already inside the entity.
        List<ProductVariant> variantList = savedProduct.getVariants();

        for (VariantRequest vReq : req.getVariants()) {
            ProductVariant variant = new ProductVariant();
            variant.setProduct(savedProduct); // Link to Parent
            variant.setSku(vReq.getSku());
            variant.setVariantName(vReq.getName());
            variant.setPrice(vReq.getPrice());
            variant.setWeightKg(vReq.getWeight());

            // Inventory
            Inventory inventory = new Inventory();
            inventory.setVariant(variant);
            inventory.setQuantityOnHand(vReq.getQuantity());
            inventory.setMinStockLevel(10);

            variant.setInventory(inventory);

            // ADD TO EXISTING LIST (Do not use setVariants)
            variantList.add(variant);
        }

        // 5. FINAL SAVE
        // We don't need to call setVariants() because we added directly to the object reference.
        return productRepository.save(savedProduct);
    }
}