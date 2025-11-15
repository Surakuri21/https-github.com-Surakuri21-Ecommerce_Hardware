package com.Surakuri.Model.dto;


import com.Surakuri.Model.entity.Products_Categories.Home_Category;
import com.Surakuri.Model.entity.Products_Categories.Deal;
import lombok.Data;

import java.util.List;

@Data
public class HomePage {

    private List<Home_Category> home;

    private List<Deal> dealsPromotion;

    private List<Home_Category> toolsEquipment;

    private List<Home_Category> buildingConstruction;

    private List<Home_Category> electricalLightning;

    private List<Home_Category> plumbingPipes;

    private List<Home_Category> gardenOutdoor;

    private List<Home_Category> specialCategories;

















}
