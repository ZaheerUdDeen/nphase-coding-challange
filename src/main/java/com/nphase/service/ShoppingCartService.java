package com.nphase.service;

import com.nphase.entity.Product;
import com.nphase.entity.ShoppingCart;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.util.List;

public class ShoppingCartService {

    public BigDecimal calculateTotalPrice(ShoppingCart shoppingCart) {
        BigDecimal totalPrice = shoppingCart.getProducts()
                .stream()
                .map(product -> product.getPricePerUnit().multiply(BigDecimal.valueOf(product.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);

        return totalPrice.subtract(rewardDiscountOnBulkPurchase(shoppingCart.getProducts()));
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
     public BigDecimal rewardDiscountOnBulkPurchase(List<Product> lineItemProductInShoppingCart) {
        return lineItemProductInShoppingCart.stream().filter(lineItemProduct -> lineItemProduct.getQuantity() > 3 ).map(product -> product.discountPrice(0.1))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
