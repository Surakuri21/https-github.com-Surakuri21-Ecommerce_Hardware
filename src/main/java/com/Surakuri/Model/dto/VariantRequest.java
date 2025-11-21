package com.Surakuri.Model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariantRequest {

    private String sku;         // e.g. "WIRE-3.5-RED"
    private String name;        // e.g. "3.5mm Red - 150m Box"

    private BigDecimal price;   // e.g. 3800.00

    private BigDecimal weight;  // e.g. 6.5 (kg)

    private int quantity;       // e.g. 20 (Initial Stock)
}