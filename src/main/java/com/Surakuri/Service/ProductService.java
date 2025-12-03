package com.Surakuri.Service;

import com.Surakuri.Model.dto.CreateProductRequest;
import com.Surakuri.Model.dto.VariantRequest;
import com.Surakuri.Model.entity.Other_Business_Entities.Seller;
import com.Surakuri.Model.entity.Products_Categories.Category;
import com.Surakuri.Model.entity.Products_Categories.Product;
import com.Surakuri.Model.entity.Products_Categories.ProductVariant;
import com.Surakuri.Repository.CategoryRepository;
import com.Surakuri.Repository.ProductRepository;
import com.Surakuri.Repository.SellerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ObjectMapper objectMapper; // For converting Map to JSON string

    @Transactional
    public Product createProduct(CreateProductRequest req, Long sellerId) {

        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        Category category = categoryRepository.findByName(req.getCategoryName());
        if (category == null) {
            category = new Category();
            category.setName(req.getCategoryName());
            categoryRepository.save(category);
        }

        Product product = new Product();
        product.setSeller(seller);
        product.setCategory(category);
        product.setName(req.getName());
        product.setBrand(req.getBrand());
        product.setDescription(req.getDescription());
        product.setImageUrl(req.getImageUrl());
        product.setCreatedAt(LocalDateTime.now());
        product.setActive(true);

        // Save parent first to get its ID
        Product savedProduct = productRepository.save(product);

        List<ProductVariant> variantList = savedProduct.getVariants();

        for (VariantRequest vReq : req.getVariants()) {
            ProductVariant variant = new ProductVariant();
            variant.setProduct(savedProduct);
            variant.setSku(vReq.getSku());
            variant.setPrice(vReq.getPrice());
            variant.setStockQuantity(vReq.getQuantity());
            variant.setMinStockLevel(10); // Default value

            // Process flexible attributes
            Map<String, String> attributes = vReq.getAttributes();
            try {
                // Store all attributes as a JSON string
                variant.setSpecifications(objectMapper.writeValueAsString(attributes));
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error processing variant attributes", e);
            }

            // Generate a user-friendly variant name from attributes
            String generatedName = attributes.values().stream().collect(Collectors.joining(" / "));
            variant.setVariantName(generatedName);

            // Extract weight from attributes if present
            if (attributes.containsKey("Weight")) {
                try {
                    // Remove "kg" and parse
                    String weightStr = attributes.get("Weight").replaceAll("[^\\d.]", "");
                    variant.setWeightKg(new BigDecimal(weightStr));
                } catch (NumberFormatException e) {
                    variant.setWeightKg(BigDecimal.ZERO); // Default if parsing fails
                }
            } else {
                variant.setWeightKg(BigDecimal.ZERO);
            }

            variantList.add(variant);
        }

        // Set default price and weight on parent product from the first variant
        if (!variantList.isEmpty()) {
            savedProduct.setPrice(variantList.get(0).getPrice());
            savedProduct.setWeightKg(variantList.get(0).getWeightKg());
        }

        return productRepository.save(savedProduct);
    }
}