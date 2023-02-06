package com.nphase.entity;

import java.math.BigDecimal;

public class Product {
    private final String name;
    private final BigDecimal pricePerUnit;
    private final int quantity;

    private final ProductCategory category;


    public Product(String name, BigDecimal pricePerUnit, int quantity, ProductCategory category) {
        this.name = name;
        this.pricePerUnit = pricePerUnit;
        this.quantity = quantity;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public int getQuantity() {
        return quantity;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public enum ProductCategory {
        drinks,
        foods,
    }

    public BigDecimal discountPrice(Double discountPercentage) {
        BigDecimal totalPrice = this.pricePerUnit.multiply(BigDecimal.valueOf(this.quantity));
       return totalPrice.multiply(BigDecimal.valueOf(discountPercentage));
    }
}
