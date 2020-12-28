package com.jonahsebright.billingtest.app_products;

import java.util.ArrayList;
import java.util.Arrays;

public class AppProducts {
    public @interface Product {
        String TEST_1 = "product_1_test";
        String TEST_2 = "product_2_test";
        String UPGRADE_PREMIUM = "upgrade_premium";
        String BUY_IMAGE = "buy_image";
    }

    public static ArrayList<String> getInAppSkuList() {
        return new ArrayList<>(Arrays.asList(
                Product.TEST_1,
                Product.TEST_2,
                Product.UPGRADE_PREMIUM,
                Product.BUY_IMAGE
        ));
    }
}
