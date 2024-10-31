package com.poly.datn.Entity.DTO;

import com.poly.datn.Entity.Product.Product;
import lombok.Data;

import java.io.Serializable;


@Data
public class Product1Dto implements Serializable {
    private Long productId;
    private String name;
    private Double price;
    private String description;
    private String imgBannerPath;
    private Integer totalQuantity;
    private Boolean available;

    private Long inventory;


    public Product1Dto(Long productId, String name, Double price, String description, String imgBannerPath, Integer totalQuantity, Boolean available, Long inventory) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imgBannerPath = imgBannerPath;
        this.totalQuantity = totalQuantity;
        this.available = available;
        this.inventory = inventory;
    }
}