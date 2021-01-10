package com.jonahsebright.billingtest.showproducts;

import com.jonahsebright.billingtest.purchases.products.AppProducts.Product;
import com.jonahsebright.billingtest.purchases.products.ProductTypeIdentifier;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductTypeIdentifierTest {

    @Test
    public void returnCorrectProductIdsOfConsumableInAppProducts() throws Exception {
        assertThat(ProductTypeIdentifier.getConsumableProductIds())
                .usingRecursiveComparison()
                .isEqualTo(new ArrayList<>(Arrays.asList("product_1_test")));
    }

    @Test
    public void returnCorrectProductIdsOfNonConsumableInAppProducts() throws Exception {
        assertThat(ProductTypeIdentifier.getNonConsumableProductIds())
                .usingRecursiveComparison()
                .isEqualTo(new ArrayList<>(Arrays.asList("product_2_test")));
    }

    @Test
    public void returnCorrectIdsOfSubscription() throws Exception {
        assertThat(ProductTypeIdentifier.getSubscriptionIds())
                .usingRecursiveComparison()
                .isEqualTo(new ArrayList<>());
    }

    @Test
    public void canTellTypeProductById() throws Exception {
        assertTrue(ProductTypeIdentifier.isConsumable(Product.TEST_1));
        assertFalse(ProductTypeIdentifier.isNonConsumable(Product.TEST_1));
        assertFalse(ProductTypeIdentifier.isSubscription(Product.TEST_1));
        assertTrue(ProductTypeIdentifier.isNonConsumable(Product.TEST_2));
        assertFalse(ProductTypeIdentifier.isConsumable(Product.TEST_2));
        assertFalse(ProductTypeIdentifier.isSubscription(Product.TEST_2));
        assertFalse(ProductTypeIdentifier.isNonConsumable("hello"));
        assertFalse(ProductTypeIdentifier.isConsumable("hello"));
        assertFalse(ProductTypeIdentifier.isSubscription("hello"));
    }
}