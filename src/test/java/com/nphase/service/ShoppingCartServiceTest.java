package com.nphase.service;


import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class ShoppingCartServiceTest {
    private final ShoppingCartService service = new ShoppingCartService();

    @Test
    public void calculatesPrice()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 2, Product.ProductCategory.drinks),
                new Product("Coffee", BigDecimal.valueOf(6.5), 1, Product.ProductCategory.drinks)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(16.5));
    }

    @Test
    public void calculatesPriceApplyingBulkItemRewardDiscount()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.0), 5, Product.ProductCategory.drinks),
                new Product("Coffee", BigDecimal.valueOf(3.5), 3, Product.ProductCategory.foods)
        ));

        BigDecimal result = service.calculateTotalPriceAfterBulkRewardDiscount(cart);

        Assertions.assertEquals(result.setScale(1, RoundingMode.HALF_UP), BigDecimal.valueOf(33.0));
    }

    @Test
    public void calculatesPriceApplyingCategoryRewardDiscount()  {
        ShoppingCart cart = new ShoppingCart(Arrays.asList(
                new Product("Tea", BigDecimal.valueOf(5.3), 2, Product.ProductCategory.drinks),
                new Product("Coffee", BigDecimal.valueOf(3.5), 2, Product.ProductCategory.drinks),
                new Product("Cheese", BigDecimal.valueOf(8.0), 2, Product.ProductCategory.foods)
        ));

        BigDecimal result = service.calculateTotalPrice(cart);

        Assertions.assertEquals(result, BigDecimal.valueOf(31.84));
    }

}