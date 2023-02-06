package com.nphase.service;

import com.nphase.config.DiscountConfiguration;
import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartService {


    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        BigDecimal totalPrice = shoppingCart.getProducts()
                .stream()
                .map(product -> product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        return totalPrice.subtract(rewardDiscountOnBulkPurchase(shoppingCart.getProducts())).subtract(discountByCategory(shoppingCart.getProducts()));
    }

    /** TASK 2
     * Requirement:
     *
     * We would like to reward the clients that are buying products in bulk.
     * If the client buys more than 3 items of the same product, we are giving him 10% discount for this product.
     *
     * Example:
     *
     * Given 2 products:
     *  -> name: tea, pricePerUnit: 5, quantity: 5
     *  -> name: coffee, pricePerUnit: 3.5, quantity: 3
     *
     * Expected total is: 22.5 (for tea) + 10.5 (for coffee) = 33.0
     *
     * @param lineItemProductInShoppingCart
     * @return
     */
     public BigDecimal rewardDiscountOnBulkPurchase(final List<Product> lineItemProductInShoppingCart) {
        return lineItemProductInShoppingCart.stream().filter(lineItemProduct -> lineItemProduct.getQuantity() > DiscountConfiguration.ItemAmount ).map(product -> product.discountPrice(DiscountConfiguration.discountPercentage))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }


    /**
     * Requirement:
     *
     * We would like to introduce the concept of item category and expand our discount policies to the entire category.
     * If the client buys more than 3 items of the product within the same category, we are giving him 10% discount for all product in this category.
     *
     * Example:
     *
     * Given 3 products:
     *  -> name: tea, pricePerUnit: 5.3, quantity: 2, category: drinks
     *  -> name: coffee, pricePerUnit: 3.5, quantity: 2, category: drinks
     *  -> name: cheese, pricePerUnit: 8, quantity: 2, category: food
     *
     * Expected total is: 9.54 (for tea) + 6.30 (for coffee) + 16 (for cheese) = 31.84
     *
     *
     * @param lineItemProductInShoppingCart
     */
    public BigDecimal discountByCategory(final List<Product> lineItemProductInShoppingCart) {
         return lineItemProductInShoppingCart.stream().collect(Collectors.groupingBy(Product::getCategory)).values()
                 .stream().filter(products -> { return products.stream().mapToInt(product -> product.getQuantity()).sum() > DiscountConfiguration.ItemAmount;
                 }).map(products -> {
                     return products.stream().map(product -> product.discountPrice(DiscountConfiguration.discountPercentage))
                             .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                 }).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
