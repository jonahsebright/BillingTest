package com.jonahsebright.billingtest.app_products;

import com.jonahsebright.billingtest.app_products.AppProducts.Product;

import java.util.ArrayList;
import java.util.Arrays;

public class ProductTypeIdentifier {
    public static ArrayList<String> getConsumableProductIds() {
        return new ArrayList<>(Arrays.asList(Product.TEST_1));
    }

    public static ArrayList<String> getNonConsumableProductIds() {
        return new ArrayList<>(Arrays.asList(Product.TEST_2));
    }

    public static ArrayList<String> getSubscriptionIds() {
        return new ArrayList<>();
    }

    public static boolean isConsumable(String productId) {
        return getConsumableProductIds().contains(productId);
    }

    public static boolean isNonConsumable(String productId) {
        return getNonConsumableProductIds().contains(productId);
    }

    public static boolean isSubscription(String productId) {
        return false;
    }
}
