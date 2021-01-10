package com.jonahsebright.billingtest.purchases.products;

import java.util.ArrayList;
import java.util.Arrays;

public class AppProducts {
    public @interface Product {
        //in app
        String TEST_1 = "product_1_test";
        String TEST_2 = "product_2_test";
        String BUY_IMAGE = "buy_image";

        //subscriptions
        String UPGRADE_PREMIUM = "upgrade_premium_abo";
        String UPGRADE_BASIC = "upgrade_basic";
    }

    public static ArrayList<String> getInAppSkuList() {
        return new ArrayList<>(Arrays.asList(
                Product.TEST_1,
                Product.TEST_2,
                Product.BUY_IMAGE
        ));
    }

    public static ArrayList<String> getSubscriptionSkuList() {
        return new ArrayList<>(Arrays.asList(
                Product.UPGRADE_BASIC,
                Product.UPGRADE_PREMIUM
        ));
    }
}
